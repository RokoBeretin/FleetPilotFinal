/**
 * Bazno sučelje Command dizajnerskog obrasca (Command pattern).
 * <p>
 * Svaka poslovna operacija u aplikaciji (stvaranje rezervacije,
 * preuzimanje vozila, povrat vozila, izravni checkout za pranje/gorivo,
 * reset svih podataka) implementirana je kao zasebna klasa koja
 * implementira ovo sučelje. GUI komponente (invokeri) time ne moraju
 * poznavati detalje izvršavanja operacije - samo pozivaju
 * {@link #execute()} nad odgovarajućom naredbom.
 */
public interface Command {
    boolean execute();
}