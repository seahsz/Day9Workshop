package project01am;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Date;

public class App {

    public static void main(String[] args) {

        List<Product> products = new ArrayList<>();

        products.add(new Product(1l, "Mouse", "For click UI on screen",
                "Computer", 99.0f, new Date()));
        products.add(new Product(2l, "Keyboard", "Device that allows alpha numerics inputs",
                "Computer", 235.5f, new Date()));
        products.add(new Product(3l, "15.6 inch monitor", "Extended display panel",
                "Computer", 157.5f, new Date()));
        products.add(new Product(4l, "Huawei Pura 70 Ultra", "Huawei phone",
                " Mobile", 900f, new Date()));
        products.add(new Product(5l, "Huawei Mate 50 Pro", "Huawei phone",
                "Mobile", 1200.0f, new Date()));
        products.add(new Product(6l, "iPhone 16 Pro", "iPhone",
                "Mobile", 2000.0f, new Date()));
        products.add(new Product(7l, "iPhone 14 Pro", "iPhone",
                "Mobile", 1800.0f, new Date()));

        List<Product> phonesAbove1500 = products.stream()
                .filter(p -> p.getProdCat().equalsIgnoreCase("mobile"))
                .filter(p -> p.getPrice() > 1500)
                .sorted((p0, p1) -> p0.getPrice().intValue() - p1.getPrice().intValue())
                .collect(Collectors.toList());

        if (args.length != 2) {
            System.err.println("Please enter: directory, filename");
            System.exit(-1);
        }

        String dirPath = args[0];
        String fileName = args[1];
        
        File dir = new File(dirPath);
        dir.mkdir();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(dirPath + "/" + fileName))) {
            for (Product p : phonesAbove1500) {
                bw.write(p.toString());
            }
        } catch (IOException e) {
            System.err.println("Error trying to write file" + e.getMessage());
        }
    }
}
