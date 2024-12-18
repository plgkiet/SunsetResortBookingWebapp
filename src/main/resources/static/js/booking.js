// amount of accommodation
document.addEventListener("DOMContentLoaded", () => {
    if (window.location.pathname === "/showproduct" ||  window.location.pathname ==="/accommodations") {
        localStorage.removeItem("bookings");
        localStorage.removeItem("totalAmount");
        localStorage.removeItem("reservations");
    }
    const h_6_Ele = document.getElementById("accommodation-reservation-message");
    const message = JSON.parse(localStorage.getItem("message"));
    if(message){
        h_6_Ele.innerText = message;
        setTimeout(() => {
            h_6_Ele.innerText = '';
            localStorage.removeItem("message");
            window.location.href="/thankyou";
        }, 3000);
    }

    let bookings = JSON.parse(localStorage.getItem("bookings")) || [];
    let totalAmount = parseInt(localStorage.getItem("totalAmount")) || 0;

    const totalAmountEl = document.getElementById("total-amount");
    const bookButtons = document.querySelectorAll(".book-btn");
    const reserveButton = document.getElementById("export-booking");
    const clearButton = document.getElementById("clear-booking");
    // let totalAmount = 0;
    let totalPrice = 0;
    // let bookings = [];

    bookButtons.forEach(button => {
        button.addEventListener("click", () => {
            const card = button.closest(".room-card");
            const roomName = card.querySelector("h3").innerText;
            const remainedQuantityEl = card.querySelector(".remained-quantity");
            const remainedQuantity = parseInt(remainedQuantityEl.innerText);
            const quantityInput = card.querySelector(".quantity");
            const quantity = parseFloat(quantityInput.value);
            const priceText = card.querySelector(".price strong").innerText;
            const price = parseFloat(priceText.replace(/[^0-9.]/g, ""));
            const accommodationId = card.querySelector(".view-btn").getAttribute("data-id") || 0;
            if (!quantity || quantity <= 0) {
                alert("Please enter a valid quantity!");
                return;
            }

            if (quantity > remainedQuantity) {
                alert("Not enough rooms available!");
                return;
            }

            totalAmount += quantity;
            totalPrice =  price * quantity;
            totalAmountEl.innerText = totalAmount;

            bookings.push({
                accommodationId: accommodationId,
                accommodationName: roomName,
                quantity: quantity,
                price:price,
                totalPrice: totalPrice,
            });
            console.log(bookings);
        });
    });
    // Handle Reservations
    reserveButton.addEventListener("click", (e) => {
        const checkInDate = document.getElementById("checkInDate").value;
        const checkOutDate = document.getElementById("checkOutDate").value;
        const numberOfGuests = document.getElementById("numberOfGuests").value;
        const generalReservation = {
            "checkInDate" : checkInDate,
            "checkOutDate" : checkOutDate,
            "numberOfGuests" : numberOfGuests
        }

        localStorage.setItem("generalReservation" , JSON.stringify(generalReservation));
        fetch("/reservations/get-reservations", {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            }
        })
            .then(response => response.json())
            .then(data =>{
                if(data.status === 'error'){
                    if(data.redirectURL) {
                        window.location.href = data.redirectURL;
                    }
                }else{
                    window.location.href=data.redirectURL;
                    localStorage.setItem("reservations",JSON.stringify((bookings)));
                    localStorage.setItem("totalAmount", totalAmount);
                }
            })

            .catch(error => console.error('Error:', error));

    });

    //Clear amount
    clearButton.addEventListener("click", () =>{
        totalAmount = 0;
        bookings.length = 0;
        localStorage.removeItem("bookings")
        localStorage.removeItem("totalAmount");
        totalAmountEl.innerText = totalAmount;
    })
});

function calculateLengthOfStay(checkInDate, checkOutDate) {
    const checkIn = new Date(checkInDate);
    const checkOut = new Date(checkOutDate);
    if (isNaN(checkIn.getTime()) || isNaN(checkOut.getTime())) {
        return "Invalid date format. Please use MM-dd-yyyy.";
    }
    const diffInMs = checkOut - checkIn;
    if (diffInMs < 0) {
        return "Check-out date must be after check-in date.";
    }
    const lengthOfStay = diffInMs / (1000 * 60 * 60 * 24);
    return lengthOfStay;
}

document.addEventListener("DOMContentLoaded", () => {
    const {checkInDate, checkOutDate} = JSON.parse(localStorage.getItem("generalReservation")) || {};
    const totalLengthOfStay = calculateLengthOfStay(checkInDate,checkOutDate);
    document.getElementById("total-length-of-stay").innerText = totalLengthOfStay;
    document.getElementById("arriving-date").innerText = new Date(checkInDate).toDateString();
    document.getElementById("departing-date").innerText = new Date(checkOutDate).toDateString();
});

document.addEventListener("DOMContentLoaded", () => {
    const reservations = JSON.parse(localStorage.getItem("reservations")) || [];
    const totalAmount = localStorage.getItem("totalAmount") || 0;
    const selectedRoomsEl = document.getElementById("selected-rooms");
    const subtotalPriceEl = document.getElementById("subtotal-price");
    const taxesFeesEl = document.getElementById("taxes-fees");
    const totalPriceEl = document.getElementById("total-price");
    // const totalAmountEl = document.getElementById("total-amount");

    let subtotal = 0;

    if (reservations.length === 0) {
        selectedRoomsEl.innerHTML = "<p>No rooms selected.</p>";
    } else {
        reservations.forEach(booking => {
            const { accommodationName, quantity, price } = booking;
            const totalRoomPrice = price * quantity;
            subtotal += totalRoomPrice;

            const roomDetails = document.createElement("p");
            roomDetails.innerText = `${quantity} x ${accommodationName} - $${totalRoomPrice}`;
            selectedRoomsEl.appendChild(roomDetails);
        });
    }

    const tax = subtotal * 0.15;
    const totalPrice = subtotal + tax;

    subtotalPriceEl.innerText = `$${subtotal.toFixed(2)}`;
    taxesFeesEl.innerText = `$${tax.toFixed(2)}`;
    totalPriceEl.innerText = totalPrice.toFixed(2);

});

function handleReserveAndPayment(event){
    event.preventDefault();
    const reservationUnitDTOList = JSON.parse(localStorage.getItem("reservations"));
    const totalQuantity = JSON.parse(localStorage.getItem("totalAmount"))
    const totalPrice = document.getElementById("total-price").innerText;
    console.log(totalPrice);
    const lengthOfStay =  document.getElementById("total-length-of-stay").innerText;
    console.log(lengthOfStay);
    const {checkInDate, checkOutDate} = JSON.parse(localStorage.getItem("generalReservation"));
    fetch("/reservations/make-reservations", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",  // Ensure this is application/json
        },
        body: JSON.stringify({
            checkInDate: checkInDate,
            checkOutDate: checkOutDate,
            totalPrice: totalPrice,
            totalQuantity: totalQuantity,
            lengthOfStay: lengthOfStay,
            reservationUnitDTOList: reservationUnitDTOList
        })
    })
        .then(response => response.json())
        .then(data => {
            if(data.status){
                if(data.status === 'error'){
                    console.log("erorrr")
                }else{
                    window.location.href = data.redirectURL;
                    localStorage.setItem("message",JSON.stringify("Successfully make reservations !!!"));
                }
            }
        })
        .catch(error => console.error('Error:', error));
}


// document.addEventListener("DOMContentLoaded", () => {
//    const staySummary = document.getElementById("summary-stay");
//    const reservations = JSON.parse(localStorage.getItem("reservations")) || [];
//    staySummary.innerHTML = '';
//    reservations.forEach((booking) => {
//        const str = `
//         <p>Room Name: ${booking.roomName}</p>
//             <p>Quantity: ${booking.quantity}</p>
//             <p>Total Price: ${booking.totalPrice}</p>`;
//        staySummary.innerHTML += str;
//    })
// });




