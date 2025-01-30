package TerminalSimulator.services.interfaces;

import TerminalSimulator.models.dto.request.Request;
import TerminalSimulator.models.dto.response.Response;

public interface CommandService {
    Response execute(Request request);
}
