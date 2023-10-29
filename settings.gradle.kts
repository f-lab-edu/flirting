rootProject.name = "flirting"

include(":app:app-service-api", ":app-point-consumer")
include(":domain", ":domain:domain-rds", ":domain:domain-redis")
include(":gateway")
include(":core")
include(":notification")