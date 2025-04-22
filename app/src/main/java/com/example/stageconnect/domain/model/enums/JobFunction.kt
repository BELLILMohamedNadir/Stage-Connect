package com.example.stageconnect.domain.model.enums

enum class JobFunction(val label: String) {
    ACCOUNTING_AND_FINANCE("Accounting and Finance"),
    ADMINISTRATOR("Administration"),
    ARCHITECTURE_AND_ENGINEERING("Architecture and Engineering"),
    ARTS_AND_SPORTS("Arts and Sports"),
    CUSTOMER_SERVICE("Customer Service"),
    EDUCATION_AND_TRAINING("Education and Training"),
    GENERAL_SERVICES("General Services"),
    HEALTH_AND_MEDICAL("Health and Medical"),
    HOSPITALITY_AND_TOURISM("Hospitality and Tourism"),
    HUMAN_RESOURCES("Human Resources"),
    IT_AND_SOFTWARE("IT and Software"),
    LEGAL("Legal"),
    MANAGEMENT_AND_CONSULTANCY("Management and Consultancy"),
    MANUFACTURING_AND_PRODUCTION("Manufacturing and procuction"),
    MEDIA_AND_CREATIVES("Media and Creatives"),
    PUBLIC_SERVICES_AND_NGOS("Public Services and NGOs"),
    SAFETY_AND_SECURITY("Safety and Security"),
    SALES_AND_MARKETING("Sales and Marketing"),
    SCIENCES("Sciences"),
    SUPPLY_CHAIN("Supply Chain"),
    WRITING_AND_CONTENT("Writing and Content");

    override fun toString(): String = label
}