role = sessionStorage.getItem('role')
if (role !== 'ROLE_ADMIN'){
    location.href = '/api/v1/auth/login';
}