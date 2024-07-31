package dao.impl;

import dao.BD;
import dao.IDao;
import model.Dentist;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.h2.command.Prepared;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DentistDAOH2 implements IDao<Dentist> {


    private static final Logger LOGGER = LogManager.getLogger(DentistDAOH2.class);

    private static final String SQL_INSERT = "INSERT INTO DENTIST (REGISTRATION, NAME, LASTNAME)" +
           "VALUES(?,?,?)";
    private static final String SQL_SELECT = "SELECT * FROM DENTIST WHERE ID=?";

    private static final String SQL_UPDATE = "UPDATE DENTIST SET REGISTRATION=?, NAME=?, LASTNAME=?" +
            "WHERE ID=?";

    private static final String SQL_DELETE = "DELETE FROM DENTIST WHERE ID=?";

    private static final String SQL_SELECT_ALL = "SELECT * FROM DENTIST";
    @Override
    public Dentist save(Dentist dentist) {
        Connection connection = null;

        try{
            LOGGER.info("Se inicio una operacion de guardado de odontologo");

            //CONECTAR A LA BD
            connection = BD.getConnection();

            //INSERTAR VALORES ---> GUARDARLOS
            PreparedStatement psIsert = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            psIsert.setInt(1, dentist.getRegistration());
            psIsert.setString(2, dentist.getName());
            psIsert.setString(3, dentist.getLastName());
            psIsert.execute();

            ResultSet rs = psIsert.getGeneratedKeys();
            while (rs.next()) {
                dentist.setId(rs.getInt(1));
                LOGGER.info("Este es el odontologo que se guardo: " +
                        dentist.getName() + " " + dentist.getLastName());
            }


        } catch (Exception e){
            LOGGER.error("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try{
                connection.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return dentist;
    }

    @Override
    public Dentist findById(Integer id) {
        Connection connection = null;
        LOGGER.info("Iniciando la busqueda de un odontologo");

        Dentist dentist = null;

        try{
            connection = BD.getConnection();
            PreparedStatement psSelect = connection.prepareStatement(SQL_SELECT);
            psSelect.setInt(1, id);

            ResultSet rs = psSelect.executeQuery();
            while(rs.next()) {
                dentist = new Dentist(rs.getInt(1), rs.getInt(2),
                        rs.getString(3), rs.getString(4));
                LOGGER.info("Consultamos el odontologo con el ID: " + dentist.getId()
                + " es: " + dentist.getName() + " " + dentist.getLastName());
            }

        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try{
                connection.close();
            } catch (Exception e) {
                LOGGER.error("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return dentist;
    }

    @Override
    public void update(Dentist dentist) {
        LOGGER.info("Iniciando la actualizacion de un odontologo...");
        Connection connection = null;

        try{
            connection = BD.getConnection();
            PreparedStatement psUpdate = connection.prepareStatement(SQL_UPDATE);
            psUpdate.setInt(1, dentist.getRegistration());
            psUpdate.setString(2, dentist.getName());
            psUpdate.setString(3, dentist.getLastName());
            psUpdate.setInt(4, dentist.getId());

            psUpdate.execute();

        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try{
                connection.close();
            } catch (Exception e) {
                LOGGER.error("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }


    }

    @Override
    public void delete(Integer id) {
        Connection connection = null;

        try{
            connection = BD.getConnection();
            PreparedStatement psDelete = connection.prepareStatement(SQL_DELETE);
            psDelete.setInt(1, id);
            psDelete.execute();
            LOGGER.warn("AVISO!! se elimino el odontologo con id: " + id);

        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try{
                connection.close();
            } catch (Exception e) {
                LOGGER.error("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Dentist> findAll() {
        Connection connection = null;
        LOGGER.info("Iniciando la busqueda de todos los odontologos");
        List<Dentist> dentistList = new ArrayList<>();
        Dentist dentist = null;

        try{
            connection = BD.getConnection();
            PreparedStatement psSelectAll = connection.prepareStatement(SQL_SELECT_ALL);

            //GUARDAMOS EN RESULT SET LA CONSULTA A LA BD
            ResultSet rs = psSelectAll.executeQuery();

            //RECORRER LA CONSULTA
            while(rs.next()) {
                //guardar esa consulta proveniente del rs en un objeto en java
                dentist = new Dentist(rs.getInt(1), rs.getInt(2), rs.getString(3),
                        rs.getString(4));

                //guardamos los odontologos en la lista
                dentistList.add(dentist);
                LOGGER.info("Encontramos los siguientes odontologos con id: " + dentist.getId() + " matricula nro: "
                + dentist.getRegistration() + ", nombre: " + dentist.getName() + " " + dentist.getLastName());
            }


        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try{
                connection.close();
            } catch (Exception e) {
                LOGGER.error("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return dentistList;
    }
}
