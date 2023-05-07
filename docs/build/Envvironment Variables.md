# Build Properties
The following environment variables are available to configure the build:

> __NOTE:__ The build properties take precedence over these environment
> variables.

> __NOTE:__ These are set from the GitHub Action secret keys during builds
> on GitHub Actions, allowing deployment to Maven Central to be automated.

## Signing Artifacts

### SIGNING_KEY_ID
The `SIGNING_KEY_ID` environment variable specifies the key id to use when
using a subkey to sign the artifacts. If a subkey is not used, this should
be blank, or not set.

### SIGNING_KEY_PRIVATE
The `SIGNING_KEY_PRIVATE` environment variable specifies the ascii-armoured
PGP private key to sign the artifacts with.

Newlines are represented as `\n`.

### SIGNING_KEY_PASSWORD
The `SIGNING_KEY_PASSWORD` environment variable specifies the password or
passphrase associated with the private key.

## Open Source Software Repository Hosting

### OSSRH_USERNAME
The `OSSRH_USERNAME` environment variable specifies the Open Source Software
Repository Hosting (OSSRH) username to use when publishing artifacts to Maven
Central.

### OSSRH_PASSWORD
The `OSSRH_PASSWORD` environment variable specifies the Open Source Software
Repository Hosting (OSSRH) password to use when publishing artifacts to Maven
Central.

---
Copyright (C) 2023 Reece H. Dunn
