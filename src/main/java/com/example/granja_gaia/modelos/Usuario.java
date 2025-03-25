package com.example.granja_gaia.modelos;

import com.example.granja_gaia.enums.Rol;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "usuario", schema = "granja_gaia", catalog = "postgres")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"token"}) // Excluye 'token' para evitar recursividad
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nickname", nullable = false, unique = true)
    @NotEmpty(message = "El nickname no puede estar vacío")
    private String nickname;

    @Column(name = "rol", nullable = false)
    @Enumerated(EnumType.STRING) // Mejor usar STRING para legibilidad
    @NotNull(message = "El rol no puede ser nulo")
    private Rol rol;

    @Column(name = "email", nullable = false, unique = true)
    @NotEmpty(message = "El email no puede estar vacío")
    private String email;

    @Column(name = "contrasena", nullable = false)
    @NotEmpty(message = "La contraseña no puede estar vacía")
    private String contrasena;

    @OneToOne(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private TokenAcceso token;


    // Métodos de UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(rol.name()));
    }

    @Override
    public String getPassword() {
        return contrasena;
    }

    @Override
    public String getUsername() {
        return nickname;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Método toString() personalizado
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", rol=" + rol +
                ", email='" + email + '\'' +
                '}';
    }

    // Método para sincronizar la relación bidireccional con TokenAcceso
    public void setToken(TokenAcceso token) {
        if (token == null) {
            //Si el token es nulo:
            if (this.token != null) {
                //Si ya había un token asignado (this.token != null),
                // le quita la referencia al Usuario
                // (es decir, le dice al token que ya no tiene dueño).
                this.token.setUsuario(null);
            }

            //Si el token no es nulo:
        } else {
            //Le asigna este Usuario como dueño al token
            //(es decir, le dice al token que su dueño es este Usuario).
            token.setUsuario(this);

        }
        //Asigna el token a este Usuario
        //(este Usuario ahora tiene este token).
        this.token = token;
    }


}