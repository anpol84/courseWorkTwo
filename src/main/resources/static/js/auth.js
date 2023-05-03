let token;
let role;
let id
const form = document.getElementById('form1');
if (form != null) {
    form.addEventListener('submit', (event) => {
        event.preventDefault(); // Отменяем отправку формы по умолчанию
        const username = form.querySelector('#username').value;
        const password = form.querySelector('#password').value;
        // Отправляем асинхронный POST-запрос на сервер для авторизации
        fetch('/api/v1/auth/login', {
            method: 'POST',
            body: JSON.stringify({username, password}),
            headers: {'Content-Type': 'application/json'}
        }).then(response => {
            if (response.ok) { // Если запрос успешен
                return response.json(); // Парсим ответ в формате JSON
            } else {
                alert("Неправильный логин или пароль")
                throw new Error(`Error ${response.status}: ${response.statusText}`);
            }
        }).then(data => {
            // Сохраняем полученный токен в localStorage или sessionStorage
            token = data.token
            role = data.role
            id = data.id
            sessionStorage.setItem('token', token)
            sessionStorage.setItem('role', role)
            sessionStorage.setItem('id', id)
            if (role != null){
                location.href = '/api/v1/users'
            }else{
                location.href = '/api/v1/shops';
            }
        }).catch(error => {
            console.error(error);
        });
    });
}

const form2 = document.getElementById('form2');
form2.addEventListener('submit', (event) => {
    event.preventDefault(); // Отменяем отправку формы по умолчанию
    const username = form2.querySelector('#username1').value;
    const password = form2.querySelector('#password1').value;
    const firstName = form2.querySelector('#firstName').value;
    const lastName = form2.querySelector('#lastName').value;
    const email = form2.querySelector('#email').value;
    fetch('/api/v1/auth/registry', {
        method: 'POST',
        body: JSON.stringify({username, password, lastName, firstName, email}),
        headers: {'Content-Type': 'application/json'}
    }).then(response => {
        if (response.ok) { // Если запрос успешен
            return response.json(); // Парсим ответ в формате JSON
        } else {
            alert("Пользователь с такими данными уже существует, либо ваши данные содержат кириллицу")
            throw new Error(`Error ${response.status}: ${response.statusText}`);
        }
    }).then(data => {
        token = data.token
        role = data.role
        id = data.id
        sessionStorage.setItem('token', token)
        sessionStorage.setItem('role', role)
        sessionStorage.setItem('id', id)
        if (role != null){
            location.href = '/api/v1/users'
        }else{
            location.href = '/api/v1/shops';
        }
    }).catch(error => {
        console.error(error);
    });
});
const signInBtn = document.querySelector('.signin-btn');
const signUpBtn = document.querySelector('.signup-btn');
const formBox = document.querySelector('.form-box');
const body = document.body;
signUpBtn.addEventListener('click', function(){
    formBox.classList.add('active');
    body.classList.add('active');
});
signInBtn.addEventListener('click', function(){
    formBox.classList.remove('active');
    body.classList.remove('active');
});