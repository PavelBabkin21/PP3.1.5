$(async function () {
    await authUser();
});

async function authUser() {

    fetch("/api/user", {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Referer': null
        }
    })
        .then((response) => response.json())
        .then(data => {
            $('#authUser').append(data.username);
            let roles = data.roles.map(role => " " + role.name);
            $('#authUserRoles').append(roles);
            let user = `$(
                <tr> 
                    <td>${data.id}</td>
                    <td>${data.name}</td>
                    <td>${data.username}</td>
                    <td>${roles}</td>
                </tr>    
                )`;
            $('#authUserPanel').append(user);
        })
}