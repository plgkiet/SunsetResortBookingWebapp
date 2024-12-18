document.addEventListener("DOMContentLoaded", () => {
    const remainedSeatEl = document.querySelector(".remained-seat span");
    const numberOfGuestsInput = document.getElementById("adults");
    const form = document.querySelector(".booking-form form");
    const noticePopup = document.getElementById("notice-popup");
    const cancelNoticeButton = document.getElementById("cancel-notice");
    const paymentForm = document.querySelector(".booking-form");
    const success = document.querySelector(".success");
    const successMessageBox = document.querySelector("._success");

    function showNoticePopup() {
        noticePopup.classList.remove("hidden");
    }

    function hideNoticePopup() {
        noticePopup.classList.add("hidden");
    }

    function showSuccessPopup() {
        paymentForm.style.display = "none";
        success.classList.remove("hidden");
        successMessageBox.style.display = "block";
    }

    form.addEventListener("submit", (e) => {
        const remainedSeats = parseInt(remainedSeatEl.innerText, 10);
        const numberOfGuests = parseInt(numberOfGuestsInput.value, 10);
        e.preventDefault();
        if (isNaN(numberOfGuests) || numberOfGuests <= 0) {
            showNoticePopup();
        } else if (numberOfGuests > remainedSeats) {
            showNoticePopup();
        } else {
            showSuccessPopup();
        }
    });

    cancelNoticeButton.addEventListener("click", () => {
        hideNoticePopup();
    });
});



