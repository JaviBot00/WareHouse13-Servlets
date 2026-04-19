<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    /*
     * Vista principal — index.jsp
     *
     * Responsabilidad (MVC):
     *   Esta es la VISTA. Es quien decide el idioma de presentación
     *   leyendo la cabecera Accept-Language del request HTTP.
     *   El Controlador no participa aquí: no sabe ni le importa
     *   en qué idioma se sirve el HTML.
     *
     * Flujo:
     *   1. JSP lee getLocale() → obtiene el idioma preferido del navegador
     *   2. Normaliza a "es" o "en" (fallback a "en")
     *   3. Inyecta la variable JS:  const LANG = 'es';
     *   4. app.js la lee y activa el diccionario correcto
     */

    String lang = "en";
    java.util.Locale locale = request.getLocale();
    if (locale != null && locale.getLanguage().equals("es")) {
        lang = "es";
    }
%>
<!DOCTYPE html>
<html lang="<%= lang %>">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>WareHouse13</title>
  <link rel="stylesheet" href="css/styles.css">
</head>
<body>

<header>
  <div class="header-logo">🏭</div>
  <h1>WareHouse13</h1>
</header>

<div class="app-body">

  <!-- ── Sidebar (desktop) ── -->
  <nav class="sidebar">
    <button class="nav-item active" data-page="add">
      <span class="nav-icon">＋</span>
      <span data-i18n="nav_add"></span>
    </button>
    <button class="nav-item" data-page="list">
      <span class="nav-icon">☰</span>
      <span data-i18n="nav_list"></span>
    </button>
    <button class="nav-item" data-page="filters">
      <span class="nav-icon">⊟</span>
      <span data-i18n="nav_filters"></span>
    </button>
    <button class="nav-item" data-page="settings">
      <span class="nav-icon">⚙</span>
      <span data-i18n="nav_settings"></span>
    </button>
  </nav>

  <!-- ── Main content ── -->
  <main class="main-content">

    <!-- ══ AÑADIR / ADD ══ -->
    <section id="page-add" class="page active">
      <div class="page-header">
        <h2 data-i18n="page_add_title"></h2>
        <p  data-i18n="page_add_sub"></p>
      </div>

      <div class="card" style="max-width: 560px;">

        <div class="switch-row">
          <div>
            <div class="switch-label" data-i18n="perishable_label"></div>
            <div class="switch-hint"  data-i18n="perishable_hint"></div>
          </div>
          <label class="toggle">
            <input type="checkbox" id="switchPerishable">
            <span class="toggle-track"></span>
            <span class="toggle-thumb"></span>
          </label>
        </div>

        <div class="form-group">
          <label for="addCode" data-i18n="label_code"></label>
          <input type="text" id="addCode" maxlength="16"
                 data-i18n-ph="label_code">
          <div class="helper-text" data-i18n="code_helper"></div>
        </div>

        <div class="form-group">
          <label for="addDesc" data-i18n="label_desc"></label>
          <input type="text" id="addDesc" data-i18n-ph="label_desc">
        </div>

        <div class="form-row">
          <div class="form-group">
            <label data-i18n="label_price"></label>
            <div class="input-prefix-wrap">
              <span class="prefix">€</span>
              <input type="number" id="addPrice" min="0" step="0.01" placeholder="0.00">
            </div>
          </div>
          <div class="form-group">
            <label for="addStock" data-i18n="label_stock"></label>
            <input type="number" id="addStock" min="0" step="1" placeholder="0">
          </div>
        </div>

        <div class="form-group expiration-field" id="expirationGroup">
          <label for="addExpiration" data-i18n="label_expiration"></label>
          <input type="date" id="addExpiration">
          <div class="helper-text" data-i18n="exp_helper"></div>
        </div>

        <button class="btn btn-primary" id="btnAdd" data-i18n="btn_add"></button>
      </div>
    </section>

    <!-- ══ INVENTARIO / INVENTORY ══ -->
    <section id="page-list" class="page">
      <div class="page-header">
        <h2 data-i18n="page_list_title"></h2>
        <p  data-i18n="page_list_sub"></p>
      </div>

      <div class="stats-row">
        <div class="stat-card">
          <div class="stat-label" data-i18n="stat_total"></div>
          <div class="stat-value" id="statTotal">—</div>
        </div>
        <div class="stat-card">
          <div class="stat-label" data-i18n="stat_nostock"></div>
          <div class="stat-value" id="statNoStock">—</div>
        </div>
        <div class="stat-card">
          <div class="stat-label" data-i18n="stat_perishable"></div>
          <div class="stat-value" id="statPerishable">—</div>
        </div>
      </div>

      <div class="table-wrap">
        <table>
          <thead>
            <tr>
              <th data-i18n="th_code"></th>
              <th data-i18n="th_desc"></th>
              <th data-i18n="th_type"></th>
              <th data-i18n="th_price"></th>
              <th data-i18n="th_stock"></th>
              <th data-i18n="th_expiration"></th>
              <th data-i18n="th_actions"></th>
            </tr>
          </thead>
          <tbody id="productTableBody">
            <tr><td colspan="7">
              <div class="empty-state"><div class="spinner"></div></div>
            </td></tr>
          </tbody>
        </table>
      </div>
    </section>

    <!-- ══ FILTROS / FILTERS ══ -->
    <section id="page-filters" class="page">
      <div class="page-header">
        <h2 data-i18n="page_filter_title"></h2>
        <p  data-i18n="page_filter_sub"></p>
      </div>

      <div class="chip-group" id="chipGroup">
        <button class="chip active" data-filter="all"       data-i18n="chip_all"></button>
        <button class="chip"        data-filter="nostock"   data-i18n="chip_nostock"></button>
        <button class="chip"        data-filter="expired"   data-i18n="chip_expired"></button>
        <button class="chip"        data-filter="prices"    data-i18n="chip_prices"></button>
        <button class="chip"        data-filter="withdrawn" data-i18n="chip_withdrawn"></button>
      </div>

      <div class="price-range-panel card" id="priceRangePanel">
        <div class="form-row" style="margin-bottom: 12px;">
          <div class="form-group" style="margin: 0;">
            <label data-i18n="label_min_price"></label>
            <div class="input-prefix-wrap">
              <span class="prefix">€</span>
              <input type="number" id="filterMinPrice" min="0" step="0.01" placeholder="0.00">
            </div>
          </div>
          <div class="form-group" style="margin: 0;">
            <label data-i18n="label_max_price"></label>
            <div class="input-prefix-wrap">
              <span class="prefix">€</span>
              <input type="number" id="filterMaxPrice" min="0" step="0.01" placeholder="999.99">
            </div>
          </div>
        </div>
        <button class="btn btn-tonal" id="btnApplyPrices"
                style="width: 100%;" data-i18n="btn_apply"></button>
      </div>

      <div class="result-count" id="filterResultCount"></div>

      <div class="table-wrap">
        <table>
          <thead>
            <tr>
              <th data-i18n="th_code"></th>
              <th data-i18n="th_desc"></th>
              <th data-i18n="th_type"></th>
              <th data-i18n="th_price"></th>
              <th data-i18n="th_stock"></th>
              <th data-i18n="th_expiration"></th>
              <th data-i18n="th_status"></th>
            </tr>
          </thead>
          <tbody id="filterTableBody">
            <tr><td colspan="7">
              <div class="empty-state">
                <p data-i18n="filter_placeholder"></p>
              </div>
            </td></tr>
          </tbody>
        </table>
      </div>
    </section>

    <!-- ══ AJUSTES / SETTINGS ══ -->
    <section id="page-settings" class="page">
      <div class="page-header">
        <h2 data-i18n="page_settings_title"></h2>
        <p  data-i18n="page_settings_sub"></p>
      </div>

      <!-- Buscador -->
      <div class="card-section-label" style="padding: 0 2px; margin-bottom: 10px;"
           data-i18n="search_section"></div>
      <div class="card" style="max-width: 560px; margin-bottom: 24px;">
        <div style="display: flex; gap: 10px; align-items: flex-end;">
          <div class="form-group" style="flex: 1; margin: 0;">
            <label for="searchCode" data-i18n="label_search"></label>
            <input type="text" id="searchCode" data-i18n-ph="label_search">
          </div>
          <button class="btn btn-tonal" id="btnSearch"
                  style="height: 40px;" data-i18n="btn_search"></button>
        </div>
        <div id="searchResults" style="margin-top: 12px;"></div>
      </div>

      <!-- BD remota -->
      <div class="card-section-label" style="padding: 0 2px; margin-bottom: 10px;"
           data-i18n="db_section"></div>
      <div class="card" style="max-width: 560px;">

        <div class="settings-row" style="margin-bottom: 16px;">
          <div class="settings-row-info">
            <div class="title" data-i18n="load_active_title"></div>
            <div class="hint"  data-i18n="load_active_hint"></div>
          </div>
          <button class="btn btn-tonal btn-sm" id="btnReloadActive"
                  data-i18n="btn_load"></button>
        </div>

        <div class="divider"></div>

        <div class="settings-row" style="margin-bottom: 4px;">
          <div class="settings-row-info">
            <div class="title" data-i18n="load_retired_title"></div>
            <div class="hint"  data-i18n="load_retired_hint"></div>
          </div>
          <button class="btn btn-outline btn-sm" id="btnLoadRetired"
                  data-i18n="btn_load"></button>
        </div>

        <div id="retiredResults" style="margin-top: 4px;"></div>
      </div>
    </section>

  </main>
</div>

<!-- ── Bottom nav (móvil) ── -->
<nav class="bottom-nav">
  <button class="nav-item active" data-page="add">
    <span class="nav-icon">＋</span>
    <span data-i18n="nav_add"></span>
  </button>
  <button class="nav-item" data-page="list">
    <span class="nav-icon">☰</span>
    <span data-i18n="nav_list"></span>
  </button>
  <button class="nav-item" data-page="filters">
    <span class="nav-icon">⊟</span>
    <span data-i18n="nav_filters"></span>
  </button>
  <button class="nav-item" data-page="settings">
    <span class="nav-icon">⚙</span>
    <span data-i18n="nav_settings"></span>
  </button>
</nav>

<!-- ── Modal: stock ── -->
<div class="modal-backdrop" id="stockModal">
  <div class="modal">
    <h3 data-i18n="modal_stock_title"></h3>
    <p class="modal-sub" id="stockModalSub"></p>
    <div class="form-group" style="margin-bottom: 0;">
      <label data-i18n="label_stock_change"></label>
      <input type="number" id="stockChangeInput" placeholder="±">
      <div class="helper-text" data-i18n="stock_change_helper"></div>
    </div>
    <div class="modal-actions">
      <button class="btn btn-outline" id="stockModalCancel"
              data-i18n="btn_cancel"></button>
      <button class="btn btn-primary" id="stockModalConfirm"
              style="width: auto; padding: 10px 20px;"
              data-i18n="btn_save"></button>
    </div>
  </div>
</div>

<!-- ── Modal: retirar ── -->
<div class="modal-backdrop" id="retireModal">
  <div class="modal">
    <h3 data-i18n="modal_retire_title"></h3>
    <p class="modal-sub" id="retireModalSub"></p>
    <div class="modal-actions">
      <button class="btn btn-outline" id="retireModalCancel"
              data-i18n="btn_cancel"></button>
      <button class="btn btn-danger-outline" id="retireModalConfirm"
              data-i18n="btn_withdraw"></button>
    </div>
  </div>
</div>

<!-- ── Toast container ── -->
<div id="toast-container"></div>

<!--
  El JSP inyecta LANG antes de cargar app.js.
  app.js lee esta variable global para activar el diccionario correcto.
-->
<script>const LANG = '<%= lang %>';</script>
<script src="js/app.js"></script>

</body>
</html>
