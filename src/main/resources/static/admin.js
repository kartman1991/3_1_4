//Common

const usersList = document.querySelector('#users')
const url = 'http://localhost:8080/rest/'

const on = (element, event, button, handler) => {
    element.addEventListener(event, e => {
        if (e.target.closest(button)) {
            handler(e)
        }
    })
}

const showUser = (data) => {
    data.forEach(
        user => {
            usersList.innerHTML += `
                    <tr>
                        <th>${user.id}</th>
                        <td>${user.username}</td>
                        <td>${user.surname}</td>
                        <td>${user.age}</td>
                        <td>${user.email}</td>
                        <td>${user.stringUserAuthorities}</td>
                        <td><a class="btn btn-info btn-sm" data-bs-toggle="modal" id="editUserBtn" data-bs-target="#editUser">Edit</a></td>
                        <td><a class="btn btn-danger btn-sm" data-bs-toggle="modal" id="deleteUserBtn" data-bs-target="#deleteUser">Delete</a></td>
                    </tr>`
        }
    )
}

fetch(url)
    .then(res => res.json())
    .then(data => showUser(data))

//Add user

const addUserForm = document.querySelector('#newUserForm')
addUserForm.addEventListener('submit', (e) => {
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            "username": e.target.addUsername.value,
            "surname": e.target.addLastname.value,
            "email": e.target.addEmail.value,
            "password": e.target.addPassword.value,
            "age": e.target.addAge.value,
            "rol": e.target.addRoles.value + " "
        })
    })
        .then(res => res.json())
        .then(data => showUser(data))
        .catch((e) => console.error(e))
})

//Edit user
let editId = null
on(document, 'click', '#editUserBtn', e => {
    editId = e.target.parentNode.parentNode.children[0].innerHTML
    document.getElementById('editUserId').value = e.target.parentNode.parentNode.children[0].innerHTML
    document.getElementById('editUsername').value = e.target.parentNode.parentNode.children[1].innerHTML
    document.getElementById('editLastname').value = e.target.parentNode.parentNode.children[2].innerHTML
    document.getElementById('editAge').value = e.target.parentNode.parentNode.children[3].innerHTML
    document.getElementById('editEmail').value = e.target.parentNode.parentNode.children[4].innerHTML
})
const editUserForm = document.querySelector('#editUserForm')
editUserForm.addEventListener('submit', (e) => {
    fetch(url + editId, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            "username": document.getElementById('editUsername').value,
            "surname": document.getElementById('editLastname').value,
            "email": document.getElementById('editEmail').value,
            "password": document.getElementById('editPassword').value,
            "age": document.getElementById('editAge').value,
            "rol": document.getElementById('editRoles').value + " "
        })
    })
        .then(res => res.json())
        .then(data => showUser(data))
        .catch((e) => console.error(e))
})

//Delete user

let delId = null
on(document, 'click', '#deleteUserBtn', e => {
    delId = e.target.parentNode.parentNode.children[0].innerHTML
    document.getElementById('delUserId').value = e.target.parentNode.parentNode.children[0].innerHTML
    document.getElementById('delUsername').value = e.target.parentNode.parentNode.children[1].innerHTML
    document.getElementById('delLastname').value = e.target.parentNode.parentNode.children[2].innerHTML
    document.getElementById('delAge').value = e.target.parentNode.parentNode.children[3].innerHTML
    document.getElementById('delEmail').value = e.target.parentNode.parentNode.children[4].innerHTML
})

const delUserForm = document.querySelector('#delUserForm')
delUserForm.addEventListener('submit', (e) => {
    fetch(url + delId, {
        method: 'DELETE'
    })
        .then(res => res.json())
        .then(data => {
            users = users.filter(user => user.data !== data)
            showUser(users)
            delUserForm.removeEventListener('submit', () => {
            })
        })
        .catch((e) => console.error(e))
})