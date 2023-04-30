function extractNumberFromString(str) {
    let result = "";
    for (let i = 0; i < str.length; i++) {
        if (!isNaN(str[i])) {
            result += str[i];
        }
    }
    return Number(result);
}
token = sessionStorage.getItem('token')
if (token == null){
    location.href = '/api/v1/auth/login';
}
p_id = document.getElementById("id")
role = sessionStorage.getItem('role')
s_id = sessionStorage.getItem('id')
if (role !== 'ROLE_ADMIN'){
    if (Number(s_id) !== extractNumberFromString(p_id.textContent)){
        location.href = '/api/v1/auth/login';
    }
}