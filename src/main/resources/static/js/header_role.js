role = sessionStorage.getItem('role')
s_id = sessionStorage.getItem('id')
if (role !== 'ROLE_ADMIN'){
    const delete1 = document.querySelector('#employees');
    delete1.remove()
    const delete2 = document.querySelector('#addresses');
    delete2.remove()
    let usersLink = document.querySelector('a[href="/api/v1/users"]');
    usersLink.setAttribute('href', '/api/v1/users/' + s_id);
}