# Signing Artifacts

## Creating a New Key
To create a new GPG key to sign the artifacts with, run:

    gpg --full-generate-key

When creating the key, you can add a comment -- e.g. "GitHub Actions" -- to
make it easier to identify what the key is for and to differentiate it from
other existing keys.

To see the list of GPG keys, run:

    gpg --list-keys

To get the short key ID, run:

    gpg --list-signatures --keyid-format 0xshort

## Distributing the Key
When publishing to Maven Central, the key used to sign the artifacts needs to
be available on at least one of the key servers it looks for. This can be done
by running one of the following commands:
1. `gpg --keyserver keyserver.ubuntu.com --send-keys [KEY_ID]`
2. `gpg --keyserver keys.openpgp.org --send-keys [KEY_ID]`
3. `gpg --keyserver pgp.mit.edu --send-keys [KEY_ID]`

Here, `[KEY_ID]` should be replaced by the 40-character ASCII HEX ID of the
key.

## Configuring the Secrets
To create an ascii-armoured file for the private key to sign the artifacts with
run the following with `[KEY_UID]` replaced with the uid of your GPG key. The
uid will either be of the form "Name Email" or "Name (Comment) Email".

    gpg --export-secret-key -a "[KEY_UID]" | tr '\n' '|' | sed -e 's/|/\\n/g' > gh-actions.txt

In your GitHub repository secrets, you can then create the following secrets:
1. `SIGNING_KEY_PRIVATE` -- Set this to the contents of the `gh-actions.txt`
   file.
2. `SIGNING_KEY_PASSWORD` -- Set this to the password or passphrase associated
   with that private key.
3. `SIGNING_KEY_ID` -- This is the short key ID format of the signing key. This
   secret is optional.

> __NOTE:__ If you get a "Could not read PGP secret key" error, check the stack
> trace in the build output. The innermost exception will contain the specific
> reason for the error.

## References
1. [Generating a new GPG key](https://docs.github.com/en/authentication/managing-commit-signature-verification/generating-a-new-gpg-key),
   GitHub Docs. GitHub, Inc.
2. [GPG](https://central.sonatype.org/publish/requirements/gpg/), The Central
   Repository Documentation. Sonatype, Inc.

---
Copyright (C) 2023 Reece H. Dunn
