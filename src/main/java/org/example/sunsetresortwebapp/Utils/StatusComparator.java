package org.example.sunsetresortwebapp.Utils;

import org.example.sunsetresortwebapp.Models.AccommodationReservation;
import org.example.sunsetresortwebapp.Models.RequestableServiceRequest;
import org.example.sunsetresortwebapp.Models.ReservableServiceReservation;

import java.util.Comparator;

public  class StatusComparator {
    public Comparator<AccommodationReservation> getStatusComparatorForAccommodationReservation(){
        Comparator<AccommodationReservation> statusComparator = Comparator.comparingInt(reservation -> {
            switch (reservation.getStatus()) {
                case PENDING: return 1;
                case CONFIRMED: return 2;
                case REJECTED: return 3;
                case CHECKED_IN: return 4;
                case CHECKED_OUT: return 5;
                default: return 6;
            }
        });
        return statusComparator;
    }
    public Comparator<ReservableServiceReservation> getStatusComparatorForReservableServiceReservation(){
        Comparator<ReservableServiceReservation> statusComparator = Comparator.comparingInt(reservation -> {
            switch (reservation.getReservationStatus()) {
                case PENDING: return 1;
                case CONFIRMED: return 2;
                case REJECTED: return 3;
                case CHECKED_IN: return 4;
                case CHECKED_OUT: return 5;
                default: return 6;
            }
        });
        return statusComparator;
    }
    public Comparator<RequestableServiceRequest> getStatusComparatorForRequestableServiceRequest(){
        Comparator<RequestableServiceRequest> statusComparator = Comparator.comparingInt(reservation -> {
            switch (reservation.getStatus()) {
                case PENDING: return 1;
                case CONFIRMED: return 2;
                case REJECTED: return 3;
                case CHECKED_IN: return 4;
                case CHECKED_OUT: return 5;
                default: return 6;
            }
        });
        return statusComparator;
    }
}
