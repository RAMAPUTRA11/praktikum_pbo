// Ambil data user dari session
const userData = JSON.parse(localStorage.getItem("userPajak"));

// Proteksi halaman: kalo blm login tendang ke index
if (!userData) {
    window.location.href = "index.html";
}

document.getElementById('user-name').innerText = userData.fullName;

async function loadDashboard() {
    try {
        // Panggil API UserController yang tadi kita bikin: /api/users/{id}/transactions
        const response = await fetch(`/api/users/${userData.id}/transactions`);
        const transactions = await response.json();

        let tableContent = "";
        let totalPajak = 0;

        transactions.forEach(item => {
            totalPajak += item.calculatedTax;
            tableContent += `
                <tr style="border-bottom: 1px solid rgba(255,255,255,0.1);">
                    <td style="padding: 15px 10px;">${item.category.name}</td>
                    <td>Rp ${item.taxableAmount.toLocaleString('id-ID')}</td>
                    <td style="text-align: right; color: #fbbf24; font-weight: 600;">
                        Rp ${item.calculatedTax.toLocaleString('id-ID')}
                    </td>
                </tr>
            `;
        });

        document.getElementById('tax-body').innerHTML = tableContent || "<tr><td colspan='3' style='text-align:center; padding:20px;'>Belum ada data pajak.</td></tr>";
        document.getElementById('total-tax').innerText = `Rp ${totalPajak.toLocaleString('id-ID')}`;

    } catch (error) {
        console.error("Error loading dashboard:", error);
    }
}

function logout() {
    localStorage.clear();
    window.location.href = "index.html";
}

// Jalankan fungsi saat halaman dimuat
loadDashboard();