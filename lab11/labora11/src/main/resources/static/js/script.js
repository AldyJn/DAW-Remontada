// Custom JavaScript for Lab 11 Application

document.addEventListener('DOMContentLoaded', function() {
    console.log('Lab 11 - Spring Security Application loaded');

    // Auto-hide alerts after 5 seconds
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(function(alert) {
        setTimeout(function() {
            alert.style.transition = 'opacity 0.5s';
            alert.style.opacity = '0';
            setTimeout(function() {
                alert.remove();
            }, 500);
        }, 5000);
    });

    // Confirm delete action
    const deleteLinks = document.querySelectorAll('a[onclick*="confirm"]');
    deleteLinks.forEach(function(link) {
        link.addEventListener('click', function(e) {
            if (!confirm('¿Estás seguro que quieres eliminar?')) {
                e.preventDefault();
            }
        });
    });
});
