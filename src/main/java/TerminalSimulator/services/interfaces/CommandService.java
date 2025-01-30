package TerminalSimulator.services.interfaces;

import TerminalSimulator.models.dto.request.Request;

public interface CommandService {
    String exectute(Request request);
}
