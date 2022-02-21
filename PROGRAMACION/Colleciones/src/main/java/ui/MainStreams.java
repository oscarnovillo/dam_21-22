package ui;


import modelo.Cliente;
import modelo.LineaCompra;
import modelo.Producto;
import modelo.ProductoCaducable;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class MainStreams {

    public static void main(String[] args) {


        List<Producto> productos = new ArrayList<>();
        Random r = new Random();

        for (int i = 0; i < 100; i++) {
            Producto p2 = new Producto(r.nextInt(100)
                    , "cono", r.nextInt(100));

            String name = ((char) (r.nextInt(26) + 'a')) + "ono";
            if (r.nextBoolean()) {

                name = ((char) (r.nextInt(26) + 'A')) + "ono";
                p2 = new ProductoCaducable(r.nextInt(100), name, r.nextInt(100), LocalDateTime.now());
            }
            p2.setNombre(name);
            productos.add(p2);
        }


        productos.stream()
                .filter(producto -> producto.getPrecio() > 50)
                .sorted((p1, p2) -> Double.compare(p2.getPrecio(), p1.getPrecio()))
                .map(Producto::getNombre)
                .filter(s -> s.length() > 5)
                .forEach(System.out::println);

        Producto p = productos.stream()
                .filter(producto -> producto.getNombre().startsWith("a")
                        && producto.getStock() < 50)
                .findAny().orElse(null);
        //.orElse(new Producto(1,"kk",1));


        System.out.println(productos.stream()
                .mapToDouble(Producto::getPrecio)
                .max()
                .orElse(0));

        double max =  productos.stream()
                .mapToDouble(Producto::getPrecio)
                .max()
                .orElse(0);
        System.out.println(
                productos.stream()
                        .filter(producto -> producto.getPrecio() == max
                               )
                        .findFirst().orElse(null));


        System.out.println(productos.stream()
                .sorted((o1, o2) -> Double.compare(o2.getPrecio(),o1.getPrecio()))
                .limit(1)
                .findFirst().orElse(null));




        System.out.println(p);

        boolean b = productos.stream().anyMatch(producto -> producto.getStock() == 0);

        System.out.println(b);

        b = productos.stream().allMatch(producto -> producto.getStock() > 0);

        System.out.println(b);
        Cliente c = new Cliente("jj","jj");

        Cliente c1 = new Cliente("jj","jj");
        productos.stream()
                .filter(producto ->
                    producto.getIngredientes().stream()
                            .noneMatch(ingrediente -> c1.getAlergenos().contains(ingrediente))
                    )
                .collect(Collectors.toList());

        c= new Cliente("1","mmm");
        c.getComprasAntiguas().add(new ArrayList<>());
        c.getComprasAntiguas().get(0).add(new LineaCompra(productos.get(0),10));
        c.getComprasAntiguas().get(0).add(new LineaCompra(productos.get(1),1));
        c.getComprasAntiguas().add(new ArrayList<>());
        c.getComprasAntiguas().get(1).add(new LineaCompra(productos.get(1),60));
        c.getComprasAntiguas().get(1).add(new LineaCompra(productos.get(2),4));

        c.getComprasAntiguas()
                .stream()
                .flatMap(Collection::stream)
                .forEach(System.out::println);

        System.out.println(c.getComprasAntiguas()
                .stream()
                .flatMapToDouble(lineaCompras ->
                        lineaCompras.stream()
                                .mapToDouble(value -> value.getProducto().getPrecio() * value.getCantidad()))
                .sum());


        c.getComprasAntiguas().stream().flatMap(Collection::stream)
                .collect(Collectors.groupingBy(LineaCompra::getProducto,Collectors.summingDouble(LineaCompra::getCantidad)))
                .entrySet().stream().sorted(Map.Entry.<Producto,Double>comparingByValue().reversed())
                .map(productoDoubleEntry ->productoDoubleEntry.getKey()+ " "+productoDoubleEntry.getValue())
                .forEach(System.out::println);

    }
}
