package br.com.raphaelcampean.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.raphaelcampean.todolist.users.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var servletPath = request.getServletPath();

        if (!servletPath.startsWith("/tasks")) {
            filterChain.doFilter(request, response);
            return;
        }

        var authorization = request.getHeader("Authorization");

        var authEncoded = authorization.substring("Basic".length()).trim();

        byte[] authDecoded = Base64.getDecoder().decode(authEncoded);
        
        var authDecodedString = new String(authDecoded);

        var authDecodedArray = authDecodedString.split(":");
        var username = authDecodedArray[0];
        var password = authDecodedArray[1];

        var user = this.userRepository.findByUsername(username);

        if (user == null) {
            response.sendError(401, "Usuário não autorizado");
            return;
        } else {
            var result = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

            if (!result.verified) {
                response.sendError(401, "Senha incorreta");
                return;
            }

            request.setAttribute("userId", user.getId());

            filterChain.doFilter(request, response);
        }

    }
    
}
