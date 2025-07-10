package com.ferreteriaSpringBoot.ferreteriaSpringBoot.controller;

import com.ferreteriaSpringBoot.ferreteriaSpringBoot.model.Clientes;
import com.ferreteriaSpringBoot.ferreteriaSpringBoot.repository.ClientesRepository;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.itextpdf.text.Document;
//import javax.swing.text.Document;
import java.util.List;


/**
 * Controlador para gestionar las vistas y operaciones de clientes en la capa de presentación.
 * Proporciona endpoints para listar, crear, editar y eliminar clientes a través de una interfaz web.
 */
@Controller
public class VistaClientes {

    /**
     * Repositorio inyectado para acceder a las operaciones de base de datos de clientes.
     */
    @Autowired
    ClientesRepository clientesRepository;

    /**
     * Muestra la lista completa de clientes.
     *
     * @param model Objeto para pasar atributos a la vista
     * @return Nombre de la vista Thymeleaf que muestra la lista de clientes
     */
    @GetMapping("vista/clientes")
    public String getAll(Model model){
        model.addAttribute("clientes", clientesRepository.findAll());
        return "clientes";
    }

    /**
     * Muestra el formulario para crear un nuevo cliente.
     *
     * @param model Objeto para pasar atributos a la vista
     * @return Nombre de la vista Thymeleaf del formulario de cliente
     */
    @GetMapping("vistaC/form")
    public String form(Model model){
        model.addAttribute("clientes", new Clientes());
        return "clientes_form";
    }

    /**
     * Procesa el envío del formulario y guarda un nuevo cliente.
     *
     * @param clientes Objeto Cliente creado con los datos del formulario
     * @param ra Atributos para enviar mensajes flash después de redireccionar
     * @return Redirección a la vista principal de clientes
     */
    @PostMapping("vistaC/save")
    public String save(@ModelAttribute Clientes clientes, RedirectAttributes ra){
        clientesRepository.save(clientes);
        ra.addFlashAttribute("mensaje", "Cliente guardado");
        return "redirect:/vista/clientes";
    }

    /**
     * Muestra el formulario de edición para un cliente existente.
     *
     * @param id Identificador único del cliente a editar
     * @param model Objeto para pasar atributos a la vista
     * @return Nombre de la vista Thymeleaf del formulario de cliente
     */
    @GetMapping("vistaC/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        Clientes clientes = clientesRepository.findById(id).orElse(null);
        model.addAttribute("clientes", clientes);
        return "clientes_form";
    }

    /**
     * Elimina un cliente existente.
     *
     * @param id Identificador único del cliente a eliminar
     * @param ra Atributos para enviar mensajes flash después de redireccionar
     * @return Redirección a la vista principal de clientes
     */
    @PostMapping("vistaC/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra){
        clientesRepository.deleteById(id);
        ra.addFlashAttribute("mensaje", "Cliente eliminado con éxito");
        return "redirect:/vista/clientes";
    }

    //Generar PDF para los clientes
    @GetMapping("/vistaC/pdf")
    public void exportarPDF(HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Clientes.pdf");

        List<Clientes> clientesList = clientesRepository.findAll(); // Asegúrate de tener fichaRepository

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        document.add(new Paragraph("Lista de clientes"));
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(5); // ajusta las columnas necesarias
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);

        // Encabezados
        table.addCell("Id Cliente");
        table.addCell("Nombre");
        table.addCell("Telefóno");
        table.addCell("Dirección");
        table.addCell("Correo");

        // Filas
        for (Clientes c : clientesList) {
            table.addCell(c.getId_cliente().toString());
            table.addCell(c.getNombre().toString());
            table.addCell(c.getTelefono());
            table.addCell(c.getDireccion());
            table.addCell(c.getCorreo());
        }

        document.add(table);
        document.close();
    }

    //Generar Excel para los clientes
    @GetMapping("/vistaC/excel")
    public void exportarExcel(HttpServletResponse response) throws Exception
    {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=Clientes.xlsx");

        List<Clientes> clientesList = clientesRepository.findAll(); // Reemplaza con tu repositorio

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Clientes");

        // Crear encabezado
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID Cliente");
        headerRow.createCell(2).setCellValue("Nombre");
        headerRow.createCell(1).setCellValue("Telefóno");
        headerRow.createCell(3).setCellValue("Dirección");
        headerRow.createCell(4).setCellValue("Correo");

        // Agregar datos
        int rowNum = 1;
        for (Clientes clientes : clientesList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(clientes.getId_cliente());
            row.createCell(1).setCellValue(clientes.getNombre());
            row.createCell(2).setCellValue(clientes.getTelefono());
            row.createCell(3).setCellValue(clientes.getDireccion());
            row.createCell(4).setCellValue(clientes.getCorreo());
        }

        // Autoajustar columnas
        for (int i = 0; i < 5; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}