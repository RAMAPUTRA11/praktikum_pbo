const API_URL = "http://localhost:8080/api/auth";

// Fungsi buat tukar tampilan Login ke Register
function toggleAuth() {
    const loginSec = document.getElementById('login-section');
    const regSec = document.getElementById('register-section');
    
    loginSec.classList.toggle('hidden');
    regSec.classList.toggle('hidden');
}
// FUNGSI REGISTER
async function handleRegister() {
    const fullName = document.getElementById('reg-name').value;
    const username = document.getElementById('reg-user').value;
    const password = document.getElementById('reg-pass').value;

    if (!fullName || !username || !password) return alert("Isi semua data!");

    const response = await fetch(`${API_URL}/register`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ fullName, username, password })
    });

    const result = await response.text();
    alert(result);
    if (response.ok) toggleAuth(); // Balik ke login kalau sukses
}

// FUNGSI LOGIN
async function handleLogin() {
    const username = document.getElementById('login-user').value;
    const password = document.getElementById('login-pass').value;

    const response = await fetch(`${API_URL}/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password })
    });

    if (response.ok) {
        const user = await response.json();
        
        // Simpan data user ke browser
        localStorage.setItem("userPajak", JSON.stringify(user));

        // Cek apakah harus Onboarding atau langsung Dashboard
        if (user.onboarded === false) {
            window.location.href = "onboarding.html";
        } else {
            window.location.href = "dashboard.html";
        }
    } else {
        alert("Login Gagal! Cek username/password.");
    }
}