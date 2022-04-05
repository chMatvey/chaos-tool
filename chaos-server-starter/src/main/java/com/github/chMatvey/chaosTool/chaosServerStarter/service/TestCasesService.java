package com.github.chMatvey.chaosTool.chaosServerStarter.service;

import com.github.chMatvey.chaosTool.chaosServerStarter.model.RemoteCallStep;
import com.github.chMatvey.chaosTool.chaosServerStarter.model.TestCases;

import java.util.List;

public interface TestCasesService {
    TestCases generateTestCases(List<RemoteCallStep> steps);
}
