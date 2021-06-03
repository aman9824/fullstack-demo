import {useState} from 'react';
import axios from 'axios';
import './App.css';

function App() {
  let [url, setUrl] = useState("");
  let [show, setShow] = useState(false);
  let [info, setInfo] = useState({
    Name: "",
    Experience: "",
    Education: "",
    Error: ""
  });

  const handleChange = event => {
    setUrl(event.target.value);
  };

  const handleSubmit = async event => {
    event.preventDefault();
    setInfo({
      Name: "",
      Experience: "",
      Education: "",
      Error: ""
    });
    setInfo({Error: ""});
    await axios.post('http://localhost:8080/api/link', {url: url}).then(response => {
      try {
        setInfo({Name: response.data.Name, Experience: response.data.Experience, Education: response.data.Education, Error: response.data.error});
        if(!response.data.error) {
          setShow(true);
        } else {
          setShow(false);
        }
      } catch (error1) {
        setShow(false);
        setInfo({Error: response.data.error});
      }
    }).catch(error => {
      console.error(error);
    });
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
          {show ? (
            <table>
              <tbody>
                <tr>
                  <td>Name</td>
                  <td>{info.Name}</td>
                </tr>
                <tr>
                  <td>Experience</td>
                  <td>{info.Experience}</td>
                </tr>
                <tr>
                  <td>Education</td>
                  <td>{info.Education}</td>
                </tr>
              </tbody>
            </table>) : (<p>{info.Error}</p>)}
        </header>
    </div>
  );
}

export default App;
