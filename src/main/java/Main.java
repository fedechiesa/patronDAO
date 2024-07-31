import dao.BD;
import dao.impl.DentistDAOH2;
import model.Dentist;
import service.DentistService;

public class Main {
    public static void main(String[] args) {

        DentistService dentistService = new DentistService(new DentistDAOH2());

        //CREAMOS LAS TABLAS
        BD.createTables();

        //CREAR OBJETOS
        Dentist dentist1 = new Dentist(132, "Gustavo", "Alejandro");
        Dentist dentist2 = new Dentist(154, "Fred", "Costas");
        Dentist dentist3 = new Dentist(789, "Joao", "Silva");
        Dentist dentist4 = new Dentist(955, "Camila", "Martinez");

        //GUARDAR EN BD

        dentistService.save(dentist1);
        dentistService.save(dentist2);
        dentistService.save(dentist3);
        dentistService.save(dentist4);

        //CONSULTAR ODONTOLOGO POR ID
        int id = 2;
        dentistService.findById(id);

        //ACTUALIZAR ALGUN DATO DE UN ODONTOLOGO
        String updateName = "Andrea";
        String updateLastName = "Mansilla";
        dentist3.setName(updateName);
        dentist3.setLastName(updateLastName);
        dentistService.update(dentist3);
        System.out.println("El nuevo dentista numero 3 es: " + dentist3.getName() + " " + dentist3.getLastName());

        //CONSULTAMOS LA LISTA ANTES DE BORRAR ALGUN REGISTRO
        dentistService.findAll();

        //BORRAR ALGUN ODONTOLOGO
        int idDelete = 4;
        dentistService.delete(idDelete);

        //CONSULTAR LISTA DE ODONTOLOGOS
        dentistService.findAll();


    }
}
