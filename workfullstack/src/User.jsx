const User = ({info}) => {
    console.log(info);
    return (
        <>
            <h1>Name: {info.Name}</h1>
            <p>Experience: {info.Experience}</p>
            <p>Education: {info.Education}</p>
        </>
    );
}

export default User;
