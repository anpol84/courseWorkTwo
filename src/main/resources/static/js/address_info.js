role = sessionStorage.getItem('role')
if (role !== 'ROLE_ADMIN'){
    location.href = '/api/v1/auth/login';
}
const shop_id = document.getElementById('shop')
if (shop_id.textContent.endsWith('null')){
    const deleteElements = document.querySelectorAll('.shop');
    deleteElements.forEach(function(button){
        button.remove();
    });
}
const employee_id = document.getElementById('employee')
if (employee_id.textContent.endsWith('null')){
    const deleteElements = document.querySelectorAll('.employee');
    deleteElements.forEach(function(button){
        button.remove();
    });
}