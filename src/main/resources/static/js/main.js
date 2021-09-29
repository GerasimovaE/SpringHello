let deleteUserId;
let editUserId;
let currentUser;
let btnUser = $('#userButton');
let btnAdmin = $('#adminButton');


$(async function () {
    await getNewUserForm();
    await getDefaultModal();
    let emailCurrentUser = $('#emailCurrentUser');
    await userFetchService.findCurrentUser(String(emailCurrentUser[0].textContent))
        .then(res => res.json())
        .then(user => currentUser = user);

    if (!btnAdmin.is(':visible')){
        hideAdminPanel();
        btnUser.removeClass("btn-light");
        btnUser.addClass("btn-primary");
    }

    await getTableWithUsers();
})


function hideAdminPanel() {
    $('#nav-tab').hide();
    $('#titleMain').text("User information-page");
    $('#title_table').text("About user");
    $('#editCol').hide();
    $('#deleteCol').hide();
}


$('#userButton').on('click', async () => {
    hideAdminPanel();

    btnUser.removeClass("btn-light");
    btnUser.addClass("btn-primary");
    btnAdmin.removeClass("btn-primary");
    btnAdmin.addClass("btn-light");
    await getTableWithUsers();
})

$('#adminButton').on('click', async () => {
    $('#nav-tab').show();
    $('#titleMain').text("Admin panel");
    $('#title_table').text("All users");
    $('#editCol').show();
    $('#deleteCol').show();

    btnUser.removeClass("btn-primary");
    btnUser.addClass("btn-light");
    btnAdmin.removeClass("btn-light");
    btnAdmin.addClass("btn-primary");
    await getTableWithUsers();
})



const userFetchService = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Referer': null
    },
    findAllUsers: async () => await fetch('api/users'),
    findAllRoles: async () => await fetch('api/roles'),
    findOneUser: async (id) => await fetch(`api/users/${id}`),
    findCurrentUser: async (email) => await fetch(`api/currentUser/${email}`),
    addNewUser: async (user) => await fetch('api/users', {method: 'POST', headers: userFetchService.head, body: JSON.stringify(user)}),
    updateUser: async (user) => await fetch(`api/users`, {method: 'PUT', headers: userFetchService.head, body: JSON.stringify(user)}),
    deleteUser: async (id) => await fetch(`api/users/${id}`, {method: 'DELETE', headers: userFetchService.head})
}

async function getTableWithUsers() {
    let table = $('#tbody');
    table.empty();

    if (btnAdmin.hasClass("btn-primary")) {
        await userFetchService.findAllUsers()
            .then(res => res.json())
            .then(users => {
                users.forEach(user => {
                    let roles = " ";
                    for (let i = 0, len = user.likedUser.length; i < len; i++) {
                        roles += user.likedUser[i].name + " ";
                    }
                    // console.log(roles);
                    let tableFilling = `$(
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.firstName}</td>
                            <td>${user.lastName}</td>
                            <td>${user.age}</td>
                            <td>${user.email}</td>
                            <td>${roles}</td>
                            <td>
                                <button type="button" data-userid="${user.id}" data-action="edit" class="btn btn-info edit-button"
                                data-toggle="modal" data-target="#someDefaultModal">Edit</button>
                            </td>
                            <td>
                                <button type="button" data-userid="${user.id}" data-action="delete" class="btn btn-danger delete-button"
                                data-toggle="modal" data-target="#someDefaultModal">Delete</button>
                            </td>
                        </tr>
                )`;
                    table.append(tableFilling);
                })
            })
    } else {

        let roles = " ";
        for (let i = 0, len = currentUser.likedUser.length; i < len; i++) {
            roles += currentUser.likedUser[i].name + " ";
        }

        let tableFilling = `$(
                        <tr>
                            <td>${currentUser.id}</td>
                            <td>${currentUser.firstName}</td>
                            <td>${currentUser.lastName}</td>
                            <td>${currentUser.age}</td>
                            <td>${currentUser.email}</td>
                            <td>${roles}</td>
                        </tr>
                )`;
        table.append(tableFilling);
    }

    // обрабатываем нажатие на любую из кнопок edit или delete
    // достаем из нее данные и отдаем модалке, которую к тому же открываем
    $("#mainTable").find('button').on('click', (event) => {
        let defaultModal = $('#someDefaultModal');
        let targetButton = $(event.target);
        let buttonUserId = targetButton.attr('data-userid');
        let buttonAction = targetButton.attr('data-action');

        defaultModal.attr('data-userid', buttonUserId);
        defaultModal.attr('data-action', buttonAction);
        defaultModal.modal('show');
    })
}

// удаляем юзера из модалки удаления
async function deleteUser(modal, id) {
    // await userFetchService.deleteUser(id);
    console.log(id);
    deleteUserId = id;
    modal.find('.modal-title').html('Delete user');
    await userFetchService.findOneUser(id)
        .then(res => res.json())
        .then(user => {
            let userFilling = `
                <div class="container col-6 text-center">
                    <div class="form-group text-center">
                        <div>
                            <label for="idUser">ID</label>
                            <input type="text" class="form-control" value="${user.id}" id="idUser" style="text-align: center" readonly>
                        </div>
                        <div>
                            <label for="firstName">First name</label>
                            <input type="text" class="form-control" value="${user.firstName}" id="firstName" style="text-align: center" readonly>
                        </div>
                        <div>
                            <label for="lastName">Last name</label>
                            <input type="text" class="form-control" value="${user.lastName}" id="lastName" style="text-align: center" readonly>
                        </div>
                        <div>
                            <label for="age">Age</label>
                            <input type="number" class="form-control" value="${user.age}" id="age" style="text-align: center" readonly>
                        </div>
                        <div>
                            <label for="email">E-mail</label>
                            <input type="text" class="form-control" value="${user.email}" id="email" autocomplete="username" style="text-align: center" readonly>
                        </div>
                        <div id="password-div">
                            <label for="password">Password</label>
                            <input type="password" class="form-control" value="${user.password}" id="password" name="password" autocomplete="current-password" style="text-align: center" readonly>
                        </div>
                        <div>
                            <label for="likedUserModal">Role</label>
                            <select multiple class="form-control" id="likedUserModal" name="likedUser" readonly>
                                   
                            </select>
                        </div>
                    </div>
                </div>             
            `;
            modal.find('.modal-body').append(userFilling);
        })

    await userFetchService.findAllRoles()
        .then(res => res.json())
        .then(roles => {
            roles.forEach(role => {
                // console.log(role)
                $('#likedUserModal')
                    .append($("<option></option>")
                        .attr("authority", role.authority)
                        .attr("id", role.id)
                        .attr("name", role.name)
                        .text(role.name));
            })
        })

    let deleteButton = `<button type="button" id="buttonModalDelete" class="btn btn-danger" onclick="deleteUserFromDataBase();" data-dismiss="modal">Delete</button>`
    let closeButton = `<button type="button" id="buttonModal" class="btn btn-secondary" data-dismiss="modal">Close</button>`

    modal.find('.modal-footer').append(closeButton);
    modal.find('.modal-footer').append(deleteButton);

}

async function deleteUserFromDataBase(){
    await userFetchService.deleteUser(deleteUserId);
    await getTableWithUsers();
}

async function editUser(modal, id) {
    editUserId = id;
    modal.find('.modal-title').html('Edit user');
    await userFetchService.findOneUser(id)
        .then(res => res.json())
        .then(user => {
            let userFilling = `
                <div class="container col-6 text-center">
                    <div class="form-group text-center">
                    <div>
                            <label for="idUser">ID</label>
                            <input type="text" class="form-control" value="${user.id}" id="idUser" style="text-align: center" readonly>
                        </div>
                        <div>
                            <label for="firstName">First name</label>
                            <input type="text" class="form-control" value="${user.firstName}" id="firstName" style="text-align: center" >
                        </div>
                        <div>
                            <label for="lastName">Last name</label>
                            <input type="text" class="form-control" value="${user.lastName}" id="lastName" style="text-align: center" >
                        </div>
                        <div>
                            <label for="age">Age</label>
                            <input type="number" class="form-control" value="${user.age}" id="age" style="text-align: center" >
                        </div>
                        <div>
                            <label for="email">E-mail</label>
                            <input type="text" class="form-control" value="${user.email}" id="email" autocomplete="username" style="text-align: center" >
                        </div>
                        <div id="password-div">
                            <label for="password">Password</label>
                            <input type="password" class="form-control" value="${user.password}" id="password" name="password" autocomplete="current-password" style="text-align: center" >
                        </div>
                        <div>
                            <label for="likedUserModal">Role</label>
                            <select multiple class="form-control" id="likedUserModal" name="likedUser" >
                                   
                            </select>
                        </div>
                    </div>
                </div>             
            `;
            modal.find('.modal-body').append(userFilling);
        })

    await userFetchService.findAllRoles()
        .then(res => res.json())
        .then(roles => {
            roles.forEach(role => {
                // console.log(role);
                $('#likedUserModal')
                    .append($("<option></option>")
                        .attr("authority", role.authority)
                        .attr("id", role.id)
                        .attr("name", role.name)
                        .text(role.name));
            })
        })

    let deleteButton = `<button type="button" id="buttonModalEdit" class="btn btn-primary" data-dismiss="modal">Edit</button>`
    let closeButton = `<button type="button" id="buttonModal" class="btn btn-secondary" data-dismiss="modal">Close</button>`

    modal.find('')
    modal.find('.modal-footer').append(closeButton);
    modal.find('.modal-footer').append(deleteButton);

    $("#buttonModalEdit").on('click', async () => {
        let id = modal.find("#idUser").val().trim();
        let firstName = modal.find("#firstName").val().trim();
        let lastName = modal.find("#lastName").val().trim();
        let age = modal.find("#age").val().trim();
        let email = modal.find("#email").val().trim();
        let password = modal.find("#password").val().trim();
        let roles = getSelectValues($(`#likedUserModal`)[0]);

        let data = {
            id: id,
            firstName: firstName,
            lastName: lastName,
            age: age,
            email: email,
            password: password,
            likedUser: []
        }

        for (let i=0, len = roles.length; i<len; i++) {
            let obj = {
                authority: roles[i].getAttribute('authority'),
                id: roles[i].getAttribute('id'),
                name: roles[i].getAttribute('authority')
            }
            // console.log(obj);
            data.likedUser.push(obj);
        }

        await userFetchService.updateUser(data);
        await getTableWithUsers();
    })


}

// что то деалем при открытии модалки и при закрытии
// основываясь на ее дата атрибутах
async function getDefaultModal() {
    $('#someDefaultModal').modal({
        keyboard: true,
        backdrop: "static",
        show: false
    }).on("show.bs.modal", (event) => {
        let thisModal = $(event.target);
        let userid = thisModal.attr('data-userid');
        let action = thisModal.attr('data-action');
        switch (action) {
            case 'edit':
                editUser(thisModal, userid);
                break;
            case 'delete':
                deleteUser(thisModal, userid);
                break;
        }
    }).on("hidden.bs.modal", (e) => {
        let thisModal = $(e.target);
        thisModal.find('.modal-title').html('');
        thisModal.find('.modal-body').html('');
        thisModal.find('.modal-footer').html('');
    })
}

async function getNewUserForm() {
    let button = $(`#nav-user_form-link`);
    let form = $(`#user-addForm`);
    let buttonAdd = $(`#buttonAddNew`);

    button.on('click', async () => {
        if ($('#likedUser').empty()) {
            await userFetchService.findAllRoles()
                .then(res => res.json())
                .then(roles => {
                    roles.forEach(role => {
                        // console.log(role)
                        $('#likedUser')
                            .append($("<option></option>")
                                .attr("authority", role.authority)
                                .attr("id", role.id)
                                .attr("name", role.name)
                                .text(role.name));
                    })
                })
        }
    })

    buttonAdd.on('click', async () => {
        let firstName = form.find("#newFirstName").val().trim();
        let lastName = form.find("#newLastName").val().trim();
        let age = form.find("#newAge").val().trim();
        let newEmail = form.find("#newEmail").val().trim();
        let password = form.find("#newPassword").val().trim();
        let roles = getSelectValues($(`#likedUser`)[0]);

        let data = {
            firstName: firstName,
            lastName: lastName,
            age: age,
            email: newEmail,
            password: password,
            likedUser: []
        }

        for (let i=0, len = roles.length; i<len; i++) {
            let obj = {
                authority: roles[i].getAttribute('authority'),
                id: roles[i].getAttribute('id'),
                name: roles[i].getAttribute('authority')
            }

            data.likedUser.push(obj);

        }

        const response = await userFetchService.addNewUser(data);
        await getTableWithUsers();

        $('.nav-tabs a[href="#' + "nav-users_table" + '"]').tab('show');

    })
}

function getSelectValues(select) {
    console.log(select);
    let result = [];
    let options = select && select.options;
    let opt;

    for (let i=0, iLen=options.length; i<iLen; i++) {
        opt = options[i];

        if (opt.selected) {
            result.push(opt);
        }
    }
    return result;
}