token = sessionStorage.getItem('token')
if (token == null){
    location.href = '/api/v1/auth/login';
}
role = sessionStorage.getItem('role')
if (role !== 'ROLE_ADMIN'){
    const card = document.querySelector('.card');
    card.remove();
    const deleteButtons = document.querySelectorAll('.delete-form');
    deleteButtons.forEach(function(button){
        button.remove();
    });
    const updateButtons = document.querySelectorAll('.update-form');
    updateButtons.forEach(function(button){
        button.remove();
    });
}