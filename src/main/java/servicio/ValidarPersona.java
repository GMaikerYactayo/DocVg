/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import controlador.PersonaC;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


@FacesValidator(value = "validarExistenciaPersona")
public class ValidarPersona implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        boolean existe = false;
        String DNIPER = value.toString().trim();

        PersonaC personaC = new PersonaC();

        try {

            if (personaC.validarExistenciaPersona(DNIPER)) {
                existe = true;
            }

        } catch (Exception e) {

        }
        if (existe) {
            FacesMessage msg = new FacesMessage("DNI existente");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
    
}
