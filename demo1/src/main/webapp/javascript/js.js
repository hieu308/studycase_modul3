

document.addEventListener('DOMContentLoaded', function () {
    var accountErrorSpan = document.getElementById('accountError');
    var accountError = accountErrorSpan ? accountErrorSpan.getAttribute('data-error') || '' : '';
    var emailErrorSpan = document.getElementById('emailError');
    var emailError = emailErrorSpan ? emailErrorSpan.getAttribute('data-error') || '' : '';
    var passErrorSpan = document.getElementById('passError');
    var passError = passErrorSpan ? passErrorSpan.getAttribute('data-error') || '' : '';
    if (accountError) {
        accountErrorSpan.textContent = accountError;
        accountErrorSpan.style.display = 'block';
        var registerModal = new bootstrap.Modal(document.getElementById('register'));
        registerModal.show();
    } else if (passError) {
        passErrorSpan.textContent = passError;
        passErrorSpan.style.display = 'block';
        var registerModal = new bootstrap.Modal(document.getElementById('register'));
        registerModal.show();
    } else if (emailError) {
        emailErrorSpan.textContent = emailError;
        emailErrorSpan.style.display = 'block';
        var registerModal = new bootstrap.Modal(document.getElementById('register'));
        registerModal.show();
    } else {
        if (accountErrorSpan) accountErrorSpan.style.display = 'none';
        if (passErrorSpan) passErrorSpan.style.display = 'none';
        if (emailErrorSpan) emailErrorSpan.style.display = 'none';
    }

    var registerModalElement = document.getElementById('register');
    if (registerModalElement) {
        registerModalElement.addEventListener('hidden.bs.modal', function () {
            if (accountErrorSpan) accountErrorSpan.style.display = 'none';
            if (emailErrorSpan) emailErrorSpan.style.display = 'none';
            if (passErrorSpan) passErrorSpan.style.display = 'none';
        });
    }

    var loginErrorSpan = document.getElementById('loginError');
    var loginError = loginErrorSpan ? loginErrorSpan.getAttribute('data-error') || '' : '';

    if (loginError) {
        loginErrorSpan.textContent = loginError;
        loginErrorSpan.style.display = 'block';
        var loginModal = new bootstrap.Modal(document.getElementById('signup'));
        loginModal.show();
    } else {
        if (loginErrorSpan) loginErrorSpan.style.display = 'none';
    }

    var loginModalElement = document.getElementById('signup');
    if (loginModalElement) {
        loginModalElement.addEventListener('hidden.bs.modal', function () {
            if (loginErrorSpan) loginErrorSpan.style.display = 'none';
        });
    }
});

