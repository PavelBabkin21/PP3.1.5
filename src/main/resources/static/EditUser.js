$(async function (id) {
    await editUser(id);
});

async function editUser(id){

    const form = document.forms["editUserForm"];
    $('#editUserRoles').empty();

    await allRoles().then(roles => {
        roles.forEach(role => {
            let option = new Option(role.name, role.id);
            option.setAttribute("id", role.name);
            $('#editUserRoles').append(option);
        })
    })

    await showUserById(id).then(user => {
        $('#editUserId').val(user.id)
        $('#editUserName').val(user.name)
        $('#editUserSurname').val(user.username)
        $('#editUserPassword').val(user.password)

        user.roles.forEach(role => {console.log(role.name);
            document.getElementById(role.name).selected = true;
        })
    })

    form.addEventListener('submit', editProcess);

    async function editProcess(event){
        event.preventDefault();

        let edituser = await userData(form);

        fetch("/api/admin/" + id, {
            method: 'PATCH',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Referer': null
            },
            body: edituser
        }).then(() => {
            $('#editFormCloseButton').click();
            showUsersTable();
        })
    }
}