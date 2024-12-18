$(document).ready(function () {
    //get datepicker from jquery
    $(function () {
        $(".datepicker").datepicker();
    });

    //show more menu infor in desktop
    $(function showMoreMenu() {
        let clickShow = $(".mobile-nav li.has-children > a");
        clickShow.click(ShowMore);

        function ShowMore(event) {
            $(this).siblings(".mobile-sub-nav").slideToggle(100);
        }
    });

    //video btn
    $(function playVideo() {
        $(".video-box").hide();
        $(".play-video-btn").click(function () {
            $(".video-box").show();
        });

        $("#close-video").click(function () {
            $(".video-box").hide();
        })
    });
    //load items
    function loadVillaItems() {
        let villas = $("#villa-items-row");
        let villasArr = [
            {dataId:452, overlay: "Luxury", imgSrc: `images/villa.png`, name: "Sunset Villa", bed: "5 ", bath: "5 ", guest: "10 ", details: "Experience unmatched comfort and elegance with panoramic views for an unforgettable stay.", price: "1,245" },
            { dataId:453,overlay: "President", imgSrc: `images/president-suite.png`, name: "President Suite", bed: "3 ", bath: "3 ", guest: "6 ", details: "Indulge in sophistication and style, featuring spacious rooms and premium services tailored to your needs.", price: "1,000" },
            { dataId:454,overlay: "Nature Expert", imgSrc: `images/over-water-bungalows.png`, name: "Overwater Bungalow", bed: "2 ", bath: "2 ", guest: "4 ", details: "Immerse yourself in tranquility and nature with direct water access and breathtaking scenic beauty.", price: "800" }
        ];
        let VillasEl = "";

        for (s of villasArr) {
            VillasEl += `
                <div class="col-lg-4 col-sm-12">
                    <div class="villa-items">
                        <div class="villa-img">
                            <p class="overlay">${s.overlay}</p>
                            <img src="${s.imgSrc}" alt="">
                        </div>
                        <div class="info" id="info">
                            <div class="stars">
                                <i class="fa fa-star" aria-hidden="true"></i>
                                <i class="fa fa-star" aria-hidden="true"></i>
                                <i class="fa fa-star" aria-hidden="true"></i>
                                <i class="fa fa-star" aria-hidden="true"></i>
                                <i class="fa fa-star" aria-hidden="true"></i>
                            </div>

                            <h3>${s.name}</h3>
                            <!-- bieu tuong -->
                            <div class="facility-icons">
                                <i class="fa-solid fa-bed" aria-hidden="true"></i><span> ${s.bed} Beds</span>
                                <i class="fa-solid fa-bath" aria-hidden="true"></i><span> ${s.bath} Baths</span> 
                                <i class="fa-solid fa-users" aria-hidden="true"></i><span> ${s.guest} Guests</span>
                            </div>
                            <p>${s.details}</p>
                            <hr>
                            <div class="price-and-details">
                                <p>from <span style="font-size: 24px; font-weight: 500">$${s.price}</span>/Night<button data-id="${s.dataId}"  class="more-details-btn orange-button" onclick="handleViewDetail(event)">More Details</button></p>
                            </div>
                        </div>
                    </div>
                </div>`;
        }
        villas.append(VillasEl);
    }
    loadVillaItems();

    //load service
    function loadService() {
        let serArr = [
            { imgSrc: `images/restaurant.png"`, title: "Reservable Service", link: "/reservable-services"},
            { imgSrc: `images/laundry.png"`, title: "Requestable Service", link: "/requestable-services"},
            { imgSrc: `images/golf.png"`, title: "General Service", link: "/general-service"}
        ];
        let serEl = "";
        for (const a of serArr) {

            serEl += `
            <div class="col-lg-4 col-sm-12">
                <div class="services">
                    <a href="${a.link}">
                        <div class="service-img-box">
                        <img src="${a.imgSrc}" alt="${a.title}">
                        </div>
                        <div class="overlay">
                            <h4>${a.title}</h4>
                        </div>
                    </a>
                </div>
            </div>
            `;
        }
        $("#service-row").append(serEl);
    }
    loadService();


    //load blogs
    function loadBlogs() {
        let blogArr = [
            { imgSrc: `images/Blog 1.jpg"`, title: "10 Ways Villas Can Make Your Holiday Enjoyable", date: "June 13, 2024" },
            { imgSrc: `images/Blog 2.jpg"`, title: "Top 8 destinations & villas you must visit in 2024", date: "June 13, 2024" },
            { imgSrc: `images/Blog 3.jpg"`, title: "Plan your days and weeks to allocate time for Family", date: "June 8, 2024" }
        ];
        let blogEl = "";
        for (const b of blogArr) {

            blogEl += `
            <div class="col-lg-4 col-sm-12">
                <div class="blogs">
                    <a href="#">
                        <div class="blog-img-box">
                            <img src="${b.imgSrc}" alt="blog-1">
                        </div>
                        <div class="overlay">
                            <h4>${b.title}</h4>
                            <p><i class="fa-regular fa-clock"></i> ${b.date}</p>
                        </div>
                    </a>
                </div>
            </div>
            `;
        }
        $("#blogs-row").append(blogEl);
    }
    loadBlogs();

    //control card
    $(function controlCard() {
        const caroselCard = document.querySelector(".carousel-card");
        const arrowBtns = document.querySelectorAll(".control i");
        const firstCardWidth = caroselCard.querySelector(".card").offsetWidth;
        const carouselChildren = [...caroselCard.children];

        let isDragging = false, startX, startScrollLeft;

        let cardPerView = Math.round(caroselCard.offsetWidth / firstCardWidth);

        carouselChildren.slice(-cardPerView).reverse().forEach(card => {
            caroselCard.insertAdjacentHTML("afterbegin", card.outerHTML);
        });

        carouselChildren.slice(0, cardPerView).forEach(card => {
            caroselCard.insertAdjacentHTML("beforeend", card.outerHTML);
        });

        arrowBtns.forEach(btn => {
                btn.addEventListener("click", () => {
                    caroselCard.scrollLeft += btn.id === "previous" ? -(firstCardWidth + 30) : (firstCardWidth + 30);
                });
            }
        );

        const dragStart = (e) => {
            isDragging = true;
            caroselCard.classList.add("dragging");
            startX = e.pageX;
            startScrollLeft = caroselCard.scrollLeft;
        }

        const dragging = (e) => {
            if (!isDragging) return;
            caroselCard.scrollLeft = startScrollLeft - (e.pageX - startX);
        }

        const dragStop = () => {
            isDragging = false;
            caroselCard.classList.remove("dragging");
        }

        caroselCard.addEventListener("mousedown", dragStart);
        caroselCard.addEventListener("mousemove", dragging);
        caroselCard.addEventListener("mouseup", dragStop);

    });

    //go-to-top
    $(".go-to-top").click(function () {
        $("html, body").animate({ scrollTop: 0 }, 1000);
    });

    $(window).scroll(function () {
        if ($(this).scrollTop() > 200) {
            $(".go-to-top").fadeIn();
        } else {
            $(".go-to-top").fadeOut();
        }
    })

    // show login form
    function showLoginForm() {
        $('#popup-container').removeClass('hidden');
        $('#login-form').removeClass('hidden');
        $('#signup-form').addClass('hidden');
        $('#toptitle').addClass('hidden');
        $('#reverse-form').addClass('hidden');
        $('#requireforbooking').addClass('hidden');
    }

    // show signup form
    function showSignupForm() {
        $('#popup-container').removeClass('hidden');
        $('#signup-form').removeClass('hidden');
        $('#login-form').addClass('hidden');
        $('#toptitle').addClass('hidden');
        $('#reverse-form').addClass('hidden');
        $('#requireforbooking').addClass('hidden');
    }



});


//edit profile and update
function enableEditing() {
    const fields = ["user-name", "user-email", "user-phone", "user-address"];
    fields.forEach(fieldId => {
        const displaySpan = document.getElementById(fieldId);
        const inputField = document.getElementById(`${fieldId}-input`);
        displaySpan.style.display = "none";
        inputField.style.display = "inline";
    });
    document.getElementById("edit-button").style.display = "none";
    document.getElementById("update-button").style.display = "inline";
}

function saveChanges() {
    const fields = ["user-name", "user-email", "user-phone", "user-dob", "user-nationality", "user-gender", "user-address"];
    fields.forEach(fieldId => {
        const displaySpan = document.getElementById(fieldId);
        const inputField = document.getElementById(`${fieldId}-input`);
        displaySpan.textContent = inputField.value;
        displaySpan.style.display = "inline";
        inputField.style.display = "none";
    });

    document.getElementById("edit-button").style.display = "inline";
    document.getElementById("update-button").style.display = "none";
}

document.getElementById("update-button").addEventListener("click", saveChanges);

// each function on sidebar in profile
function showProfile() {
    $('#profile').removeClass('hidden');
    $('#booking-detail').addClass('hidden');
    $('#purchase-history').addClass('hidden');
    $('#change-password').addClass('hidden');
    $('#logout-popup').addClass('hidden');
}

function showBookingDetail() {
    $('#booking-detail').removeClass('hidden');
    $('#profile').addClass('hidden');
    $('#purchase-history').addClass('hidden');
    $('#change-password').addClass('hidden');
    $('#logout-popup').addClass('hidden');
}

function showPurchaseHistory() {
    $('#profile').addClass('hidden');
    $('#booking-detail').addClass('hidden');
    $('#purchase-history').removeClass('hidden');
    $('#change-password').addClass('hidden');
    $('#logout-popup').addClass('hidden');
}

function showChangePassword() {
    $('#profile').addClass('hidden');
    $('#booking-detail').addClass('hidden');
    $('#purchase-history').addClass('hidden');
    $('#change-password').removeClass('hidden');
    $('#logout-popup').addClass('hidden');
}

// show function from homepage
$(document).ready(function () {
    const urlParams = new URLSearchParams(window.location.search);
    const section = urlParams.get('section');

    switch (section) {
        case 'profile':
            $('#profile').removeClass('hidden');
            break;
        case 'booking':
            $('#booking-detail').removeClass('hidden');
            break;
        case 'history':
            $('#purchase-history').removeClass('hidden');
            break;
        case 'password':
            $('#change-password').removeClass('hidden');
            break;
        default:
            $('#profile').removeClass('hidden');
            break;
    }
});


// log out
function showLogoutPopup() {
    $('#logout-popup').removeClass('hidden');
}


function hideLogoutPopup() {
    $('#logout-popup').addClass('hidden');
}

// Request for Cancel
function showRequestForCancel(){
    $('#cancel-popup').removeClass('hidden');
}
function hideRequestForCancel(){
    $('#cancel-popup').addClass('hidden');
}
function handleRequestForCancel(event
){
    event.preventDefault();
    const bookingID = event.target.getAttribute("bookingID");
    const itemType =  event.target.getAttribute("itemType");
    showRequestForCancel();
    const yesButton  = document.getElementById('confirm-cancel');
    yesButton.addEventListener("click", function (){
        handleCancel(bookingID,itemType);
    });
}
function handleCancel(bookingId, itemType){
    let url = "";
    switch(itemType){
        case "requestableServiceRequest":
            url = "/requestable-services/cancel-requests"
            break;
        case "accommodationReservation":
           url = "/accommodations/cancel-reservations";
            break;
        case "reservableServiceReservation" :
            url = "/reservable-services/cancel-reservations";
            break;

    }
    fetch(url, {
        method:"POST",
        headers:{
            "Content-Type": "application/json",
        },
        body:JSON.stringify({
            bookingID : bookingId
        })
    }).then(response => response.json())
        .then(data => {
            if(data.status && data.status == 'success'){
                if(data.redirectUrl){
                    window.location.href= data.redirectUrl;
                }
            }
        })
        .catch(error => console.error('Error:', error))
}
$(document).ready(function () {
    $('.sidebar ul li:nth-child(5) a').click(function (e) {
        e.preventDefault();
        showLogoutPopup();
    });

    $('#confirm-logout').click(function () {
        hideLogoutPopup();
        window.location.href = "/logout/user";
    });

    $('#cancel-logout').click(function () {
        hideLogoutPopup();
    });
});



document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".toggle-password").forEach(function (toggle) {
        toggle.addEventListener("click", function () {
            this.classList.toggle("fa-eye");
            this.classList.toggle("fa-eye-slash");

            const input = document.querySelector(this.getAttribute("toggle"));

            if (input.type === "password") {
                input.type = "text";
            } else {
                input.type = "password";
            }
        });
    });

});
document.addEventListener("DOMContentLoaded", function () {
     document.getElementById("accommodations-title").addEventListener("click", function (){

         console.log("Click")
         window.location.href="/accommodations";
     });

    // Select the profile message element
    const messageProfileElement = document.getElementById("profile-message");
    if (messageProfileElement) {
        console.log("Profile message found!");

        // Hide the message after 3 seconds
        setTimeout(() => {
            messageProfileElement.style.display = "none";
            console.log("Profile message hidden.");
        }, 3000);
    } else {
        console.log("No tag with the id 'profile-message'");
    }
});

function handleViewDetail(e){
    const accommodationId = e.target.getAttribute("data-id");
    window.location.href= "/accommodations/detail-booking/" + accommodationId;

}
function handleReserveReservableService(event){
    const reservableServiceId = event.target.getAttribute('data-id');
    window.location.href= "/reservable-services/reserving/" + reservableServiceId;
}
function handleRequestService(event){
    const serviceId =  event.target.getAttribute('data-id');
    window.location.href = "/requestable-services/requesting/" + serviceId;
}
