package com.quantus.backend.controllers.system;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-09-07
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.url.prefix}/system/menu")
public class MenuController {}
