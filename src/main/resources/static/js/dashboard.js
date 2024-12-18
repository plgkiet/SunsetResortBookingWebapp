











function showSection(sectionId) {
    document.querySelectorAll('.section-content').forEach(section => {
        section.classList.remove('active');
    });
    document.getElementById(sectionId).classList.add('active');
}

function handleDeleteUser(event) {
   const userId = event.target.getAttribute("data-id");
   fetch("/users/delete-user/" + userId,{
       method: "DELETE",
       headers: {
           "Content-Type": "application/json",
       },
   }).then(response => response.json())
       .then(data => {
           if(data.status && data.status == 'success'){
                    if(data.redirectUrl){
                        window.location.href= data.redirectUrl;
                        localStorage.setItem("default-section", JSON.stringify('user-management'));
                        localStorage.setItem("result",JSON.stringify( {message:'Infor : Successfully delete user',type :'user-message'}));
                    }
           }else{
               window.location.href="/signin";
           }
       })
}

document.addEventListener('DOMContentLoaded', () => {

    //Handle Sections and Notifications
    const sectionId = JSON.parse(localStorage.getItem("default-section"));
    const result =JSON.parse(localStorage.getItem("result"));
    console.log(result);
    if(result){
        console.log(result);
        const pEl = document.getElementById(result.type);
        pEl.innerText = result.message;
        setTimeout(() =>{
            pEl.innerText = '';
            localStorage.removeItem("result");
        }, 3000)
    }
    if(!sectionId){
        showSection('user-management');
    }else{
        showSection(sectionId);
    }
});

// log out
function showLogoutPopup() {
    $('#logout-popup').removeClass('hidden');
    document.querySelectorAll('.section-content').forEach(section => {
        section.classList.remove('active');
    });
}

function hideLogoutPopup() {
    $('#logout-popup').addClass('hidden');
}

$(document).ready(function () {
    $('.sidebar ul li:nth-child(5) a').click(function (e) {
        e.preventDefault();
        showLogoutPopup();
    });

    $('#confirm-logout').click(function () {
        hideLogoutPopup();
        window.location.href = "/logout/admin";
    });

    $('#cancel-logout').click(function () {
        hideLogoutPopup();
    });
});
const accommodationRows = document.querySelectorAll(".accommodation-row-id");
accommodationRows.forEach((row) => row.addEventListener("click", function handleViewAccommodationReservationDetail(event){

    const accommodationReservationId  = row.getAttribute("reservation-id");
    fetch("/accommodation-reservation-details/" + accommodationReservationId)
        .then(response=> response.json())
        .then(data => {
            if(data.status == 'success'){
                if(data.details){
                    var detailsBody = document.getElementById('reservation-details-body');
                    detailsBody.innerHTML = "";  // Clear any existing content
                    let stt = 0;
                    data.details.forEach(function(detail) {
                        var row = document.createElement("tr");
                        row.innerHTML = `
                     <td>${stt++}</td>
                    <td>${detail.accommodationReservationDetailId}</td>
                    <td>${detail.accommodationReservationName}</td>
                     <td>${detail.reservedQuantity}</td>
                      <td>${detail.accommodationReservationTotalPrice  ? detail.accommodationReservationTotalPrice :'' }</td>
        `;
                        detailsBody.appendChild(row);
                    });
                    $('#reservationDetailsModal').modal('show');
                }
            }
        })
        .catch(error => {
            console.error('Error fetching reservation details:', error);
        });

}))


//Handlers
function handleAccommodationReservationStatus(event,status){
    const accommodationReservationId = event.target.getAttribute("data-id");
    fetch("/accommodations/update-reservation-status",{
        method:"POST",
        headers: {
            "Content-Type": "application/json",
        },
        body:JSON.stringify({
            bookingID: accommodationReservationId,
            status : status
        })
    }).then(response =>  response.json()).then(data => {
            if(data.status && data.status == 'success'){
                if(data.redirectUrl){
                    window.location.href= data.redirectUrl;
                    localStorage.setItem("result",JSON.stringify( {message:'Infor : Successfully update status',type :'accommodation-message'}));
                  localStorage.setItem("default-section", JSON.stringify('accommodation'));
                }
            }else{
               if(data.redirectUrl){
                   window.location.href= data.redirectUrl;
                   localStorage.removeItem("")
               }
            }
        })
        .catch((error) => console.log(error));
}
function handleUpdateRequestStatus(event,status){
    const requestId = event.target.getAttribute("data-id");
    fetch("/requestable-services/update-request-status",{
        method:"POST",
        headers: {
            "Content-Type": "application/json",
        },
        body:JSON.stringify({
            bookingID: requestId,
            status : status
        })
    }).then(response =>  response.json()).then(data => {
        if(data.status && data.status == 'success'){
            if(data.redirectUrl){
                window.location.href= data.redirectUrl;
                localStorage.setItem("result",JSON.stringify( {message:'Infor : Successfully update status',type :'request-message'}));
                localStorage.setItem("default-section", JSON.stringify('requests'));
            }
        }else{
            if(data.redirectUrl){
                window.location.href= data.redirectUrl;
            }
        }
    })
        .catch((error) => console.log(error));
}
function handleUpdateRSRStatus(event,status){
    const requestId = event.target.getAttribute("data-id");
    fetch("/reservable-services/update-reservation-status",{
        method:"POST",
        headers: {
            "Content-Type": "application/json",
        },
        body:JSON.stringify({
            bookingID: requestId,
            status : status
        })
    }).then(response =>  response.json()).then(data => {
        if(data.status && data.status == 'success'){
            if(data.redirectUrl){
                window.location.href= data.redirectUrl;
                localStorage.setItem("result",JSON.stringify( {message:'Infor : Successfully update status',type :'reservation-message'}));
                localStorage.setItem("default-section", JSON.stringify('reserve'));
            }
        }else{
            if(data.redirectUrl){
                window.location.href= data.redirectUrl;
            }
        }
    })
        .catch((error) => console.log(error));
}