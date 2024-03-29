package com.algaworks.algafoodapi.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

/*Controller didático para sabermos qual container
* estará sendo chamado*/
@RestController
public class HostCheckController {

    @GetMapping("/hostcheck")
    public String checkHost() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress() //pega o ip local do container que ta respondendo.
                + " - " + InetAddress.getLocalHost().getHostName();
    }

}
