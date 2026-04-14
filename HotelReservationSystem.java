import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Scanner;
import java.sql.Statement;
import java.sql.ResultSet;

public class HotelReservationSystem{
    private static final String url = "jdbc:mysql://localhost:3306/hotel_reservation";
    private static final String username = "root";
    private static final String password = "Pass@123";

    public static void main(String[] args) throws ClassNotFoundException {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        try(Connection connection = DriverManager.getConnection(url,username,password); Scanner sc = new Scanner(System.in)){

            System.out.println();
            System.out.println("Hotel Management System");
            System.out.println("1. Reserve a room");
            System.out.println("2. View Reservation");
            System.out.println("3. Get Room Number");
            System.out.println("4. Update Reservation");
            System.out.println("5. Delete Reservation");
            System.out.println("0. Exit");

            while(true) {
                System.out.println("Choose an Option: ");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        reserveRoom(connection, sc);
                        break;
                    case 2:
                        viewReservation(connection);
                        break;
                    case 3:
                        getRoomNumber(connection, sc);
                        break;
                    case 4:
                        updateReservation(connection, sc);
                        break;
                    case 5:
                        deleteReservation(connection, sc);
                        break;
                    case 0:
                        exit();

                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }
    private static void reserveRoom(Connection connection, Scanner sc){
        try{
            System.out.print("Enter guest name:");
            String guestName = sc.next();
            sc.nextLine();
            System.out.print("Enter room number: ");
            int roomNumber = sc.nextInt();
            System.out.print("Enter contact number: ");
            String contactNumber = sc.next();

            String sql = "INSERT INTO reservations (guest_name, room_number, guest_contact) VALUES ('"
                    + guestName + "', " + roomNumber + ", '" + contactNumber + "')";
            try(Statement stmt = connection.createStatement()){
                int rowAffected = stmt.executeUpdate(sql);
                if(rowAffected>0){
                    System.out.println("Reservation Successful");
                }else{
                    System.out.println("Reservation Failed");
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    private static void viewReservation(Connection connection){
        String Query = "SELECT reservation_id, guest_name, room_number, guest_contact, reservation_date from reservations;";
        try(Statement stmt = connection.createStatement()){
            ResultSet resultSet = stmt.executeQuery(Query);
            System.out.println("Current Reservations: ");
            while(resultSet.next()){
                int Reservation_id = resultSet.getInt("reservation_id");
                String guest_name = resultSet.getString("guest_name");
                int Room_number = resultSet.getInt("room_number");
                String contact = resultSet.getString("guest_contact");
                String date = resultSet.getTimestamp("reservation_date").toString();
                System.out.println("ID: "+Reservation_id);
                System.out.println("Guest Name: "+ guest_name);
                System.out.println("Room Number: "+ Room_number);
                System.out.println("Contact: "+ contact);
                System.out.println("Date/Time: "+ date);

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private static void getRoomNumber(Connection connection, Scanner sc){
        try{
            System.out.println("Enter reservation ID: ");
            int reservation_id = sc.nextInt();
            System.out.println("Enter Guest Name: ");
            String guest_name = sc.next();
            sc.nextLine();

            String Query = "SELECT room_number FROM reservations WHERE reservation_id = "
                    + reservation_id + " AND guest_name = '" + guest_name + "'";
                    try(Statement stmt = connection.createStatement()){
                        ResultSet resultSet = stmt.executeQuery(Query);
                        if(resultSet.next()){
                            int RoomNumber = resultSet.getInt("room_number");
                            System.out.println("Room Number for Guest "+guest_name+" is: "+ RoomNumber);
                        }else{
                            System.out.println("No Room Reserved for Guest: "+ guest_name);
                        }

                    }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void updateReservation(Connection connection, Scanner sc){
        try{
            System.out.print("Enter the Reservation Id to update: ");
            int reservationID = sc.nextInt();

            if(!ReservationExists(connection, reservationID)){
                System.out.println("Reservation does not found for reservation ID: "+ reservationID);
                return;
            }
            System.out.println("Fill Update Information: ");
            System.out.print("Enter new Guest Name: ");
            String updated_name = sc.next();
            System.out.print("Enter new Room Number: ");
            int updated_room_number = sc.nextInt();
            System.out.print("Enter new Contact Number: ");
            String updated_contact = sc.next();

            String Query = "UPDATE reservations SET guest_name = '" + updated_name +
                    "', room_number = " + updated_room_number +
                    ", guest_contact = '" + updated_contact +
                    "' WHERE reservation_id = " + reservationID;
            try(Statement stmt = connection.createStatement()){
                int rowAffected = stmt.executeUpdate(Query);
                if(rowAffected > 0){
                    System.out.println("Reservation Update Successfully");
                }else{
                    System.out.println("Reservation Updation Failed");
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void deleteReservation(Connection connection, Scanner sc){
        try{
            System.out.print("Enter the ReservationId to delete: ");
            int reservationId = sc.nextInt();
            if(!ReservationExists(connection, reservationId)){
                System.out.println("Reservation does not found for reservation ID: "+ reservationId);
                return;
            }

            String Query = "DELETE FROM reservations WHERE reservation_id = "+ reservationId;
            try(Statement stmt = connection.createStatement()){
                int rowAffected = stmt.executeUpdate(Query);

                if(rowAffected>0){
                    System.out.println("Reservation Deleted Successfully");
                }else{
                    System.out.println("Reservation Deletion Failed!");
                }
            }
        }catch (SQLException e ){
            e.printStackTrace();
        }
    }

    private static boolean ReservationExists(Connection connection, int reservationId){
        try{
            String Query = "SELECT reservation_id from reservations WHERE reservation_id ="+ reservationId;

                Statement stmt = connection.createStatement();
                ResultSet resultSet = stmt.executeQuery(Query);
                return resultSet.next();

        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static void exit() throws InterruptedException{
        System.out.print("Existing System");
        int i=5;
        while(i!=0){
            System.out.print(".");
            Thread.sleep(500);
            i--;
        }
        System.out.println();
        System.out.println("Thank You, for using HOTEL MANAGEMENT SYSTEM!!!!");
    }
}