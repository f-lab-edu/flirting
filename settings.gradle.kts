rootProject.name = "flirting"

include(":app:app-service-api")
include(":domain", ":domain:domain-rds")
include(":gateway")
include(":core")
include(":notification")