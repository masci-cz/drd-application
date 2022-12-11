# DrD Application
Helping application for CZ Dračí doupě desktop game

## Test locally

set environment variables for signing

* Find your key-id (using  `gpg --list-secret-keys --keyid-format=long` )
* Export the gpg secret key to an ASCII file using `gpg --export-secret-keys -a  > secret.txt`
* Export public key to file `gpg --export -a -o public.txt`
* Export secret key to file `gpg --export-secret-keys -a -o secret.txt`

```shell
  gpg --list-secret-keys --keyid-format=long
  gpg --export -a -o public.txt <key-id>
  gpg --export-secret-keys -a -o secret.txt <key-id>
  # windows
  set JRELEASER_GPG_PUBLIC_KEY=public.txt
  set JRELEASER_GPG_SECRET_KEY=secret.txt
  set JRELEASER_GPG_PASSPHRASE=%GPG_PASSPHRASE%
  # linux
  export JRELEASER_GPG_PUBLIC_KEY=public.txt
  export JRELEASER_GPG_SECRET_KEY=secret.txt
  export JRELEASER_GPG_PASSPHRASE=%GPG_PASSPHRASE%
```

Run only assemble
```shell
  mvn clean package -Passemble -Drevision=1.0.0 -Djreleaser.dry.run=true
```

Run release
```shell
  mvn clean deploy -Passemble -Prelease -Drevision=1.0.0 -Djreleaser.dry.run=true -Djreleaser.signing.mode=FILE
```