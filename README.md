# Muzean

- [Repositório](https://github.com/falcaopetri/muzean)
- [Wiki](https://github.com/falcaopetri/muzean/wiki)

## Dependências

- Instalar a biblioteca midiutil: `pip install midiutil`

Opcional:
- Instalar o software timidity para executar arquivos MIDI
- Usar o site [SolMiRe](https://solmire.com/) para converter MIDI -> MP3


## Arduino ("experimental")

Modificar o gerador de código utilizado em `Main.java#116`:

`
GeradorArduino g = new GeradorArduino();
`

O código é gerado no mesmo arquivo que o código Python seria gerado (com extensão .py).

Seriam necessárias algumas verificações especiais, mas isso foge de nosso escopo.
Por exemplo, o range de notas do MIDI é um pouco diferente das constantes para Arduino que estamos usando.

Nesse [Arduino Virtual](https://circuits.io/circuits/3879511-muzean-arduino) é possível rodar o código gerado.
O primeiro caso de teste do gerador já está carregado no site.
