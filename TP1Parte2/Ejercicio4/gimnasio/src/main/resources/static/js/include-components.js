// js/include-components.js
(async function() {
    // Cargar todos los componentes que tengan data-include
    async function loadIncludes() {
        const includes = document.querySelectorAll('[data-include]');
        for (const el of includes) {
            const url = el.getAttribute('data-include');
            try {
                const resp = await fetch(url);
                if (!resp.ok) throw new Error(`Cannot load ${url}: ${resp.status}`);
                const text = await resp.text();
                el.innerHTML = text;
            } catch (e) {
                console.error(e);
                el.innerHTML = `<!-- Error cargando ${url} -->`;
            }
        }
    }

    // Cargar scripts en orden secuencial
    function loadScript(src) {
        return new Promise((resolve, reject) => {
            const s = document.createElement('script');
            s.src = src;
            s.onload = () => resolve(src);
            s.onerror = () => reject(new Error(`Error loading ${src}`));
            document.body.appendChild(s);
        });
    }

    // Lista de scripts (misma que antes, en orden)
    const scripts = [
        "js/jquery-3.3.1.min.js",
        "js/jquery-migrate-3.0.1.min.js",
        "js/jquery-ui.js",
        "js/popper.min.js",
        "js/bootstrap.min.js",
        "js/owl.carousel.min.js",
        "js/jquery.stellar.min.js",
        "js/jquery.countdown.min.js",
        "js/bootstrap-datepicker.min.js",
        "js/jquery.easing.1.3.js",
        "js/aos.js",
        "js/jquery.fancybox.min.js",
        "js/jquery.sticky.js",
        "js/jquery.mb.YTPlayer.min.js",
        "js/main.js"
    ];

    try {
        await loadIncludes();
        // Después de inyectar los componentes, cargamos scripts en orden
        for (const s of scripts) {
            // eslint-disable-next-line no-await-in-loop
            await loadScript(s);
        }
        // opción: disparar un evento para indicar que todo ya está cargado
        window.dispatchEvent(new Event('componentsAndScriptsLoaded'));
    } catch (e) {
        console.error('Error inicializando la página:', e);
    }
})();
