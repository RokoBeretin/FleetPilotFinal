import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class AUX_CLS {
    private static Gson gson = new Gson();
    private static CarDAO carDAO = new CarDAO();

    public static HashMap<String, ArrayList<String>> readFromJson(String filePath) {
        HashMap<String, ArrayList<String>> hashMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            hashMap = gson.fromJson(reader, HashMap.class);
            System.out.println("Loaded from JSON file: " + filePath);
        } catch (IOException e) {
            System.err.println("Error reading JSON file: " + e.getMessage());
        }
        return hashMap;
    }

    public static void writeToTxt(String str, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filePath), true))) {
            writer.write(str);
            writer.newLine();
            System.out.println("Saved to text file: " + str);
        } catch (IOException e) {
            System.err.println("Error writing to text file: " + e.getMessage());
        }
    }

    public static ArrayList<String> readLinesFromTxt(String filePath) {
        ArrayList<String> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
            System.out.println("Loaded lines from text file: " + filePath);
        } catch (FileNotFoundException e) {
            System.err.println("Error reading text file: " + e.getMessage());
        }
        return lines;
    }

    public static void removeLineFromTxt(String filePath, String title) {
        if (filePath == null || filePath.isEmpty()) {
            System.out.println("File path is null or empty.");
            return;
        }

        if (title == null || title.isEmpty()) {
            System.out.println("Title is null or empty.");
            return;
        }

        try {
            List<String> lines = Files.readAllLines(Path.of(filePath));
            boolean removed = lines.removeIf(line -> line.trim().equals(title.trim()));

            if (removed) {
                Files.write(Path.of(filePath), lines);
                System.out.println("Line matching title \"" + title + "\" removed.");
            } else {
                System.out.println("No exact match found for title \"" + title + "\".");
            }

        } catch (IOException e) {
            System.err.println("Error updating file: " + e.getMessage());
        }
    }


    public static ArrayList<Car> loadCarsFromDb() {
        List<Car> carsFromDb = carDAO.getAllCars();
        System.out.println("Cars loaded from Aiven database.");
        return new ArrayList<>(carsFromDb);
    }

    public static void resetAllData(String returnsPath, String checkoutPath) {
        List<Car> cars = carDAO.getAllCars();
        for (Car car : cars) {
            carDAO.updateCarAvailability(car.getLicensePlate(), true);
        }

        try {
            Files.deleteIfExists(Path.of(returnsPath));
            Files.deleteIfExists(Path.of(checkoutPath));
            createEmptyTxtFile(returnsPath);
            createEmptyTxtFile(checkoutPath);
            System.out.println("All data reset successfully in database and files.");
        } catch (IOException e) {
            System.err.println("Error resetting data: " + e.getMessage());
        }
    }

    public static void createEmptyTxtFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.err.println("An error occurred while creating empty file.");
            e.printStackTrace();
        }
    }
}