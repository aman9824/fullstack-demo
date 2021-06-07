import {useState} from 'react';
import axios from 'axios';
import './App.css';

function App() {
  let [url, setUrl] = useState("");
  let [show, setShow] = useState(false);
  let [info, setInfo] = useState({
    Data: ""
  });
  let [Profiles, setProfiles] = useState([]);
  let [del, setDel] = useState(null);
  let [error, setError] = useState(false);

  const handleChange = event => {
    setUrl(event.target.value);
  };

  const handleSubmit = async event => {
    event.preventDefault();
    setInfo({
      Data: "",
      Error: ""
    });
    setInfo({Error: ""});
    await axios.post('http://localhost:8080/api/link', {url: url}).then(response => {
      try {
        setInfo({Data: response.data.Data, Error: response.data.error});
        if(response.data.data) {
          setDel(null);
          setShow(true);
        } else {
          setDel(null);
          setShow(false);
        }
      } catch (error1) {
        setDel(null);
        setShow(false);
        setInfo({Error: response.data.error});
      }
    }).catch(error => {
      setDel(null);
      setError(true);
    });
  };

  const handleProfiles = async () => {
    await axios.get('http://localhost:8080/api/profiles').then(response => {
      if(response.data.length === 0) {
        setProfiles([]);
      } else {
        setDel(null);
        setProfiles(response.data)
      }
    }).catch(error => {
      console.error(error);
    });
  };

  const handleDelete = async () => {
    await axios.get('http://localhost:8080/api/remove').then(response => {
      setDel(response.data);
    }).catch(error => {
        console.error(error);
    })
  };


  return (
      <div className="App">
        <header className="App-header">
          <img src="https://d33wubrfki0l68.cloudfront.net/70a0d3db1b72d5c767896100e4b22796fc343339/b5429/images/wolflogo.svg" className="App-logo" alt="logo" />
          <form onSubmit={handleSubmit}>
            <p>Enter The LinkedIn Profile Link: </p>
            <input placeholder="Enter Link" className="" value={url} onChange={handleChange} />
            <button className="btn" type="submit"> GET PROFILE DETAILS!! </button>
          </form>
          <button className="btn" onClick={handleProfiles}> GET Previous Profile Search's </button>
          <button className="btn" onClick={handleDelete}> Delete Profile Search's </button>
          {show ? (
            <table>
              <tbody>
                <tr>
                  <td>Data</td>
                  <td>{info.Data}</td>
                </tr>
              </tbody>
            </table>) : (<p>{info.Error}</p>)}
            {error ? (<p>API Unable</p>) : null}
            {Profiles.map(a => {return(<li>{a}</li>)})}
            {del}
        </header>
    </div>
  );
}

export default App;
