document.addEventListener("DOMContentLoaded", function () {
    const requestButtons = document.querySelectorAll(".btn-custom");

    requestButtons.forEach(button => {
        button.addEventListener("click", function (event) {
            event.preventDefault();
            const serviceName = this.closest(".service-section").querySelector("h2").textContent.trim();
            sessionStorage.setItem("requestedService", serviceName);
            window.location.href = '/requestable-booking';
        });
    });
});

document.addEventListener("DOMContentLoaded", function () {
    const serviceName = sessionStorage.getItem("requestedService");

    if (serviceName) {
        const serviceParagraph = document.querySelector(".booking-form p");
        serviceParagraph.textContent = `- ${serviceName}`;
    }
});

document.addEventListener("DOMContentLoaded", function () {
    const payButton = document.querySelector(".btn-custom");
    const successMessageBox = document.querySelector("._success");
    const paymentForm = document.querySelector(".booking-form");
    const container = document.querySelector(".container.hidden");


    payButton.addEventListener("click", function (event) {
        event.preventDefault();

        container.classList.remove("hidden");
        successMessageBox.style.display = "block";

        paymentForm.style.display = "none";
    });
});


