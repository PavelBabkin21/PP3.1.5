$(async function (id) {
    await deleteUser(id);
});

async function deleteUser(id) {

    await showUserById(id).then(user => {
        $('#deleteUserId').val(user.id)
        $('#deleteName').val(user.name)
        $('#deleteUsername').val(user.username)
        user.roles.forEach(role => {
            let option = new Option(role.name, role.id);
            $('#deleteUserRoles').append(option);
        })
    })

    const form = document.forms["deleteUserForm"];
    form.addEventListener('submit', delProcess)

    async function delProcess(event) {
        event.preventDefault();
        fetch("/api/admin/" + id, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Referer': null
            }
        }).then(() => {
                $('#delFormCloseButton').click();
                showUsersTable();
            }
        )
    }
}