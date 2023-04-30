role = sessionStorage.getItem('role')
if (role !== 'ROLE_ADMIN'){
    const deleteForm = document.querySelector('.employees');
    deleteForm.remove()
}