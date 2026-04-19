/* ═══════════════════════════════════════════════════
   WareHouse13 — app.js
   La variable LANG es inyectada por el JSP antes de
   cargar este fichero:
     <script>const LANG = '${lang}';</script>
   ═══════════════════════════════════════════════════ */

const BASE = '/WareHouse13-Servlets';

/* ── Diccionario i18n ───────────────────────────────────────────────────── */
const I18N = {
  es: {
    nav_add:          'Añadir',
    nav_list:         'Inventario',
    nav_filters:      'Filtros',
    nav_settings:     'Ajustes',

    page_add_title:   'Añadir producto',
    page_add_sub:     'Rellena los datos para registrar un nuevo artículo en el almacén.',
    page_list_title:  'Inventario activo',
    page_list_sub:    'Todos los productos disponibles en el almacén.',
    page_filter_title:'Filtros',
    page_filter_sub:  'Filtra el inventario por categoría o rango de precio.',
    page_settings_title: 'Ajustes',
    page_settings_sub:   'Busca productos por código y gestiona la base de datos.',

    perishable_label: 'Producto perecedero',
    perishable_hint:  'Activa si tiene fecha de caducidad',
    label_code:       'Código del producto',
    code_helper:      'Máximo 16 caracteres, letras y números.',
    label_desc:       'Descripción',
    label_price:      'Precio',
    label_stock:      'Stock inicial',
    label_expiration: 'Fecha de caducidad',
    exp_helper:       'Se almacena en formato YYYYMMDD.',
    btn_add:          '＋ Añadir al almacén',

    stat_total:       'Total productos',
    stat_nostock:     'Sin stock',
    stat_perishable:  'Perecederos',

    th_code:          'Código',
    th_desc:          'Descripción',
    th_type:          'Tipo',
    th_price:         'Precio',
    th_stock:         'Stock',
    th_expiration:    'Caducidad',
    th_actions:       'Acciones',
    th_status:        'Estado',

    chip_all:         'Todos',
    chip_nostock:     'Sin stock',
    chip_expired:     'Caducados',
    chip_prices:      'Rango de precio',
    chip_withdrawn:   'Retirados',

    label_min_price:  'Precio mínimo',
    label_max_price:  'Precio máximo',
    btn_apply:        'Aplicar filtro',

    search_section:   'Buscar por código',
    label_search:     'Código (parcial o completo)',
    btn_search:       'Buscar',
    db_section:       'Base de datos remota (MySQL)',
    load_active_title:'Consultar activos',
    load_active_hint: 'Vuelve a cargar todos los productos activos desde la BD',
    load_retired_title:'Consultar retirados',
    load_retired_hint:'Muestra todos los productos marcados como retirados',
    btn_load:         'Cargar',

    badge_normal:     'Normal',
    badge_perishable: 'Perecedero',
    badge_active:     'Activo',
    badge_retired:    'Retirado',
    badge_nostock:    'Sin stock',
    badge_expired:    'Caducado',

    btn_stock:        'Stock',
    btn_withdraw:     'Retirar',
    btn_reactivate:   'Reactivar',
    btn_delete:       'Eliminar',
    btn_cancel:       'Cancelar',
    btn_save:         'Guardar',

    modal_stock_title:'Editar stock',
    modal_stock_sub:  'Producto: {code} · Stock actual: {stock}',
    label_stock_change:'Cambio de stock',
    stock_change_helper:'Positivo para añadir, negativo para reducir.',
    modal_retire_title:'Retirar producto',
    modal_retire_sub: '¿Retirar "{desc}" ({code})?',

    confirm_delete:   '¿Eliminar permanentemente el producto {code}?',

    toast_added:      'Producto {code} añadido correctamente.',
    toast_stock_ok:   'Stock de {code} actualizado a {stock}.',
    toast_retired:    'Producto {code} retirado.',
    toast_reactivated:'Producto {code} reactivado.',
    toast_deleted:    'Producto {code} eliminado.',
    toast_loaded:     '{count} productos activos cargados desde la BD.',

    err_required:     'Rellena todos los campos obligatorios.',
    err_number:       'Introduce un número válido.',
    err_negative_stock:'El stock no puede ser negativo.',
    err_search_empty: 'Introduce un código para buscar.',
    err_connection:   'Error de conexión con el servidor.',
    err_load:         'Error al cargar productos.',

    no_results:       'No hay productos con este filtro.',
    no_products:      'No hay productos en el almacén.',
    no_search:        'Sin resultados.',
    no_retired:       'No hay productos retirados.',
    filter_placeholder:'Selecciona un filtro para ver resultados.',
    result_count:     '{count} resultado',
    result_count_pl:  '{count} resultados',
  },

  en: {
    nav_add:          'Add',
    nav_list:         'Inventory',
    nav_filters:      'Filters',
    nav_settings:     'Settings',

    page_add_title:   'Add product',
    page_add_sub:     'Fill in the details to register a new item in the warehouse.',
    page_list_title:  'Active inventory',
    page_list_sub:    'All products currently available in the warehouse.',
    page_filter_title:'Filters',
    page_filter_sub:  'Filter the inventory by category or price range.',
    page_settings_title:'Settings',
    page_settings_sub:  'Search products by code and manage the database.',

    perishable_label: 'Perishable product',
    perishable_hint:  'Enable if the product has an expiration date',
    label_code:       'Product code',
    code_helper:      'Up to 16 characters, letters and numbers.',
    label_desc:       'Description',
    label_price:      'Price',
    label_stock:      'Initial stock',
    label_expiration: 'Expiration date',
    exp_helper:       'Stored as YYYYMMDD.',
    btn_add:          '＋ Add to warehouse',

    stat_total:       'Total products',
    stat_nostock:     'Out of stock',
    stat_perishable:  'Perishable',

    th_code:          'Code',
    th_desc:          'Description',
    th_type:          'Type',
    th_price:         'Price',
    th_stock:         'Stock',
    th_expiration:    'Expiration',
    th_actions:       'Actions',
    th_status:        'Status',

    chip_all:         'All',
    chip_nostock:     'Out of stock',
    chip_expired:     'Expired',
    chip_prices:      'Price range',
    chip_withdrawn:   'Withdrawn',

    label_min_price:  'Min price',
    label_max_price:  'Max price',
    btn_apply:        'Apply filter',

    search_section:   'Search by code',
    label_search:     'Code (partial or full)',
    btn_search:       'Search',
    db_section:       'Remote database (MySQL)',
    load_active_title:'Query active products',
    load_active_hint: 'Reloads all active products from the database',
    load_retired_title:'Query retired products',
    load_retired_hint:'Shows all products marked as retired',
    btn_load:         'Load',

    badge_normal:     'Standard',
    badge_perishable: 'Perishable',
    badge_active:     'Active',
    badge_retired:    'Retired',
    badge_nostock:    'No stock',
    badge_expired:    'Expired',

    btn_stock:        'Stock',
    btn_withdraw:     'Retire',
    btn_reactivate:   'Reactivate',
    btn_delete:       'Delete',
    btn_cancel:       'Cancel',
    btn_save:         'Save',

    modal_stock_title:'Edit stock',
    modal_stock_sub:  'Product: {code} · Current stock: {stock}',
    label_stock_change:'Stock change',
    stock_change_helper:'Positive to add, negative to reduce.',
    modal_retire_title:'Retire product',
    modal_retire_sub: 'Retire "{desc}" ({code})?',

    confirm_delete:   'Permanently delete product {code}?',

    toast_added:      'Product {code} added successfully.',
    toast_stock_ok:   'Stock for {code} updated to {stock}.',
    toast_retired:    'Product {code} retired.',
    toast_reactivated:'Product {code} reactivated.',
    toast_deleted:    'Product {code} deleted.',
    toast_loaded:     '{count} active products loaded from the database.',

    err_required:     'Please fill in all required fields.',
    err_number:       'Enter a valid number.',
    err_negative_stock:'Stock cannot be negative.',
    err_search_empty: 'Enter a code to search.',
    err_connection:   'Connection error.',
    err_load:         'Error loading products.',

    no_results:       'No products match this filter.',
    no_products:      'No products in the warehouse.',
    no_search:        'No results found.',
    no_retired:       'No retired products.',
    filter_placeholder:'Select a filter to see results.',
    result_count:     '{count} result',
    result_count_pl:  '{count} results',
  }
};

/* La variable LANG es inyectada por el JSP. Fallback a 'en'. */
const T = I18N[typeof LANG !== 'undefined' && I18N[LANG] ? LANG : 'en'];

/* Sustituye {placeholders} en las cadenas del diccionario */
function t(key, vars = {}) {
  let str = T[key] || key;
  Object.entries(vars).forEach(([k, v]) => { str = str.replace(`{${k}}`, v); });
  return str;
}

/* ── Aplicar textos i18n al DOM ─────────────────────────────────────────── */
function applyI18n() {
  document.querySelectorAll('[data-i18n]').forEach(el => {
    el.textContent = t(el.dataset.i18n);
  });
  document.querySelectorAll('[data-i18n-ph]').forEach(el => {
    el.placeholder = t(el.dataset.i18nPh);
  });
}

/* ── Navigation ─────────────────────────────────────────────────────────── */
function initNav() {
  document.querySelectorAll('.nav-item[data-page]').forEach(btn => {
    btn.addEventListener('click', () => {
      const target = btn.dataset.page;
      document.querySelectorAll('.page').forEach(p => p.classList.remove('active'));
      document.querySelectorAll('.nav-item[data-page]').forEach(n => n.classList.remove('active'));
      document.querySelectorAll(`[data-page="${target}"]`).forEach(el => el.classList.add('active'));
      document.getElementById(`page-${target}`).classList.add('active');
      if (target === 'list') loadProducts();
      if (target === 'filters') applyCurrentFilter();
    });
  });
}

/* ── Toast ──────────────────────────────────────────────────────────────── */
function toast(msg, type = 'success') {
  const el = document.createElement('div');
  el.className = `toast ${type}`;
  el.textContent = msg;
  document.getElementById('toast-container').appendChild(el);
  setTimeout(() => el.remove(), 3200);
}

/* ── API helpers ────────────────────────────────────────────────────────── */
async function api(endpoint, method = 'GET', params = {}) {
  const url = new URL(BASE + endpoint, location.origin);
  let opts = { method };

  if (method === 'GET' || method === 'DELETE') {
    Object.entries(params).forEach(([k, v]) => url.searchParams.set(k, v));
  } else {
    opts.body = new URLSearchParams(params);
    opts.headers = { 'Content-Type': 'application/x-www-form-urlencoded' };
  }

  const res = await fetch(url.toString(), opts);
  return res.json();
}

/* ── Format helpers ─────────────────────────────────────────────────────── */
function fmtPrice(p) { return `${parseFloat(p).toFixed(2)} €`; }

function fmtDate(d) {
  if (!d || d === 'null') return '—';
  const s = String(d);
  if (s.length !== 8) return d;
  return `${s.slice(6,8)}/${s.slice(4,6)}/${s.slice(0,4)}`;
}

function isExpired(expDate) {
  if (!expDate || expDate === 'null') return false;
  const s = String(expDate);
  const exp = new Date(s.slice(0,4), parseInt(s.slice(4,6)) - 1, parseInt(s.slice(6,8)));
  return exp < new Date();
}

function isPerishable(p) {
  return p.expirationDate && p.expirationDate !== 'null' && p.expirationDate !== null;
}

function dateToYYYYMMDD(dateStr) {
  return dateStr ? dateStr.replace(/-/g, '') : '';
}

/* ── Row builder ────────────────────────────────────────────────────────── */
function buildRow(p, showActions = true) {
  const perishable = isPerishable(p);
  const expired    = isExpired(p.expirationDate);
  const noStock    = p.stock === 0;
  const tr         = document.createElement('tr');

  const typeChip = perishable
    ? `<span class="badge badge-perishable">${t('badge_perishable')}</span>`
    : `<span class="badge badge-normal">${t('badge_normal')}</span>`;

  const expCell = perishable
    ? `<span style="color:${expired ? 'var(--error)' : 'var(--warning)'};">${fmtDate(p.expirationDate)}${expired ? ' ⚠' : ''}</span>`
    : '—';

  const stockCell = noStock
    ? `<span style="color:var(--text-secondary);">0</span>`
    : p.stock;

  let lastCell;
  if (showActions) {
    lastCell = `
      <div class="row-actions">
        <button class="btn btn-tonal btn-sm"
                onclick="openStockModal('${p.code}', ${p.stock})">
          ${t('btn_stock')}
        </button>
        <button class="btn btn-danger-outline btn-sm"
                onclick="openRetireModal('${p.code}', '${p.description.replace(/'/g, "\\'")}')">
          ${t('btn_withdraw')}
        </button>
      </div>`;
  } else {
    const statusChip = p.retired
      ? `<span class="badge badge-retired">${t('badge_retired')}</span>`
      : noStock
        ? `<span class="badge badge-nostock">${t('badge_nostock')}</span>`
        : expired
          ? `<span class="badge badge-perishable">${t('badge_expired')}</span>`
          : `<span class="badge badge-normal">${t('badge_active')}</span>`;
    lastCell = statusChip;
  }

  tr.innerHTML = `
    <td><span class="code-mono">${p.code}</span></td>
    <td>${p.description}</td>
    <td>${typeChip}</td>
    <td>${fmtPrice(p.price)}</td>
    <td>${stockCell}</td>
    <td>${expCell}</td>
    <td>${lastCell}</td>
  `;
  return tr;
}

/* ── Inventory ──────────────────────────────────────────────────────────── */
let allProducts = [];

async function loadProducts() {
  const tbody = document.getElementById('productTableBody');
  tbody.innerHTML = `<tr><td colspan="7"><div class="empty-state"><div class="spinner"></div></div></td></tr>`;
  try {
    const data = await api('/listar-activos');
    allProducts  = Array.isArray(data) ? data : [];
    renderProductTable(allProducts);
    updateStats(allProducts);
  } catch {
    tbody.innerHTML = `<tr><td colspan="7"><div class="empty-state"><p>${t('err_load')}</p></div></td></tr>`;
  }
}

function renderProductTable(products) {
  const tbody = document.getElementById('productTableBody');
  tbody.innerHTML = '';
  if (!products.length) {
    tbody.innerHTML = `<tr><td colspan="7"><div class="empty-state"><div class="empty-icon">📦</div><p>${t('no_products')}</p></div></td></tr>`;
    return;
  }
  products.forEach(p => tbody.appendChild(buildRow(p, true)));
}

function updateStats(products) {
  document.getElementById('statTotal').textContent     = products.length;
  document.getElementById('statNoStock').textContent   = products.filter(p => p.stock === 0).length;
  document.getElementById('statPerishable').textContent = products.filter(p => isPerishable(p)).length;
}

/* ── Add product ────────────────────────────────────────────────────────── */
function initAddForm() {
  document.getElementById('switchPerishable').addEventListener('change', function () {
    document.getElementById('expirationGroup').classList.toggle('visible', this.checked);
  });

  document.getElementById('btnAdd').addEventListener('click', async () => {
    const code       = document.getElementById('addCode').value.trim();
    const desc       = document.getElementById('addDesc').value.trim();
    const price      = document.getElementById('addPrice').value;
    const stock      = document.getElementById('addStock').value;
    const perishable = document.getElementById('switchPerishable').checked;
    const expRaw     = document.getElementById('addExpiration').value;

    if (!code || !desc || price === '' || stock === '') {
      toast(t('err_required'), 'error'); return;
    }

    const params = { code, description: desc, price, stock };
    if (perishable && expRaw) params.expirationDate = dateToYYYYMMDD(expRaw);

    try {
      const res = await api('/insertar', 'POST', params);
      if (res.status === 'OK') {
        toast(t('toast_added', { code }));
        ['addCode','addDesc','addPrice','addStock','addExpiration'].forEach(id => {
          document.getElementById(id).value = '';
        });
        document.getElementById('switchPerishable').checked = false;
        document.getElementById('expirationGroup').classList.remove('visible');
      } else {
        toast(res.error || t('err_connection'), 'error');
      }
    } catch {
      toast(t('err_connection'), 'error');
    }
  });
}

/* ── Stock modal ────────────────────────────────────────────────────────── */
let stockTargetCode  = null;
let stockCurrentValue = 0;

function openStockModal(code, current) {
  stockTargetCode   = code;
  stockCurrentValue = current;
  document.getElementById('stockModalSub').textContent =
    t('modal_stock_sub', { code, stock: current });
  document.getElementById('stockChangeInput').value = '';
  document.getElementById('stockModal').classList.add('open');
  setTimeout(() => document.getElementById('stockChangeInput').focus(), 80);
}

function initStockModal() {
  document.getElementById('stockModalCancel').addEventListener('click', () => {
    document.getElementById('stockModal').classList.remove('open');
  });

  document.getElementById('stockModalConfirm').addEventListener('click', async () => {
    const delta    = parseInt(document.getElementById('stockChangeInput').value);
    if (isNaN(delta)) { toast(t('err_number'), 'error'); return; }
    const newStock = stockCurrentValue + delta;
    if (newStock < 0) { toast(t('err_negative_stock'), 'error'); return; }

    const current = allProducts.find(p => p.code === stockTargetCode);
    if (!current) { toast(t('err_connection'), 'error'); return; }

    try {
      const res = await api('/actualizar', 'PUT', {
        code: stockTargetCode,
        description: current.description,
        price: current.price,
        stock: newStock
      });
      if (res.status === 'OK') {
        toast(t('toast_stock_ok', { code: stockTargetCode, stock: newStock }));
        document.getElementById('stockModal').classList.remove('open');
        loadProducts();
      } else {
        toast(res.error || t('err_connection'), 'error');
      }
    } catch {
      toast(t('err_connection'), 'error');
    }
  });
}

/* ── Retire modal ───────────────────────────────────────────────────────── */
let retireTargetCode = null;

function openRetireModal(code, desc) {
  retireTargetCode = code;
  document.getElementById('retireModalSub').textContent =
    t('modal_retire_sub', { desc, code });
  document.getElementById('retireModal').classList.add('open');
}

function initRetireModal() {
  document.getElementById('retireModalCancel').addEventListener('click', () => {
    document.getElementById('retireModal').classList.remove('open');
  });

  document.getElementById('retireModalConfirm').addEventListener('click', async () => {
    try {
      const res = await api('/retirar', 'PUT', { code: retireTargetCode });
      if (res.status === 'OK') {
        toast(t('toast_retired', { code: retireTargetCode }));
        document.getElementById('retireModal').classList.remove('open');
        loadProducts();
      } else {
        toast(res.error || t('err_connection'), 'error');
      }
    } catch {
      toast(t('err_connection'), 'error');
    }
  });
}

/* ── Filters ────────────────────────────────────────────────────────────── */
let currentFilter = 'all';
let cachedActive  = [];

async function applyCurrentFilter() {
  await applyFilter(currentFilter);
}

function initFilters() {
  document.querySelectorAll('#chipGroup .chip').forEach(chip => {
    chip.addEventListener('click', () => {
      document.querySelectorAll('#chipGroup .chip').forEach(c => c.classList.remove('active'));
      chip.classList.add('active');
      currentFilter = chip.dataset.filter;
      document.getElementById('priceRangePanel').classList.toggle('visible', currentFilter === 'prices');
      if (currentFilter !== 'prices') applyFilter(currentFilter);
    });
  });

  document.getElementById('btnApplyPrices').addEventListener('click', () => applyFilter('prices'));
}

async function applyFilter(filter) {
  const tbody = document.getElementById('filterTableBody');
  tbody.innerHTML = `<tr><td colspan="7"><div class="empty-state"><div class="spinner"></div></div></td></tr>`;

  try {
    let products = [];

    if (filter === 'withdrawn') {
      const data = await api('/listar-retirados');
      products = Array.isArray(data) ? data : [];
    } else {
      if (!cachedActive.length) {
        const data = await api('/listar-activos');
        cachedActive = Array.isArray(data) ? data : [];
      }
      products = [...cachedActive];
    }

    if (filter === 'nostock') {
      products = products.filter(p => p.stock === 0);
    } else if (filter === 'expired') {
      products = products.filter(p => isPerishable(p) && isExpired(p.expirationDate));
    } else if (filter === 'prices') {
      const minP = parseFloat(document.getElementById('filterMinPrice').value) || 0;
      const maxP = parseFloat(document.getElementById('filterMaxPrice').value) || Infinity;
      products   = products.filter(p => parseFloat(p.price) >= minP && parseFloat(p.price) <= maxP);
    }

    const count = products.length;
    document.getElementById('filterResultCount').textContent =
      count === 1 ? t('result_count', { count }) : t('result_count_pl', { count });

    tbody.innerHTML = '';
    if (!count) {
      tbody.innerHTML = `<tr><td colspan="7"><div class="empty-state"><p>${t('no_results')}</p></div></td></tr>`;
      return;
    }
    products.forEach(p => tbody.appendChild(buildRow(p, false)));

  } catch {
    tbody.innerHTML = `<tr><td colspan="7"><div class="empty-state"><p>${t('err_load')}</p></div></td></tr>`;
  }
}

/* ── Settings — search ──────────────────────────────────────────────────── */
function initSettings() {
  const btnSearch  = document.getElementById('btnSearch');
  const searchCode = document.getElementById('searchCode');

  btnSearch.addEventListener('click', async () => {
    const code = searchCode.value.trim();
    if (!code) { toast(t('err_search_empty'), 'error'); return; }

    const container = document.getElementById('searchResults');
    container.innerHTML = '<div class="spinner"></div>';

    try {
      const data     = await api('/buscar', 'GET', { code });
      const products = Array.isArray(data) ? data : [];

      if (!products.length) {
        container.innerHTML = `<p style="font-size:13px;color:var(--text-secondary);margin-top:8px;">${t('no_search')}</p>`;
        return;
      }

      const wrap = document.createElement('div');
      wrap.className = 'inline-table-wrap';
      const tbl  = document.createElement('table');
      tbl.innerHTML = `<thead><tr>
        <th>${t('th_code')}</th><th>${t('th_desc')}</th>
        <th>${t('th_price')}</th><th>${t('th_stock')}</th><th>${t('th_status')}</th>
      </tr></thead>`;
      const tb = document.createElement('tbody');
      products.forEach(p => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
          <td><span class="code-mono">${p.code}</span></td>
          <td>${p.description}</td>
          <td>${fmtPrice(p.price)}</td>
          <td>${p.stock}</td>
          <td>${p.retired
            ? `<span class="badge badge-retired">${t('badge_retired')}</span>`
            : `<span class="badge badge-normal">${t('badge_active')}</span>`}</td>
        `;
        tb.appendChild(tr);
      });
      tbl.appendChild(tb);
      wrap.appendChild(tbl);
      container.innerHTML = '';
      container.appendChild(wrap);
    } catch {
      container.innerHTML = `<p style="font-size:13px;color:var(--error);">${t('err_connection')}</p>`;
    }
  });

  searchCode.addEventListener('keydown', e => {
    if (e.key === 'Enter') btnSearch.click();
  });

  /* Reload active */
  document.getElementById('btnReloadActive').addEventListener('click', async () => {
    cachedActive = [];
    try {
      const data = await api('/listar-activos');
      cachedActive = Array.isArray(data) ? data : [];
      toast(t('toast_loaded', { count: cachedActive.length }));
    } catch {
      toast(t('err_connection'), 'error');
    }
  });

  /* Load retired */
  document.getElementById('btnLoadRetired').addEventListener('click', async () => {
    const container = document.getElementById('retiredResults');
    container.innerHTML = '<div class="spinner"></div>';
    try {
      const data     = await api('/listar-retirados');
      const products = Array.isArray(data) ? data : [];

      if (!products.length) {
        container.innerHTML = `<p style="font-size:13px;color:var(--text-secondary);margin-top:8px;">${t('no_retired')}</p>`;
        return;
      }

      const wrap = document.createElement('div');
      wrap.className = 'inline-table-wrap';
      const tbl  = document.createElement('table');
      tbl.innerHTML = `<thead><tr>
        <th>${t('th_code')}</th><th>${t('th_desc')}</th>
        <th>${t('th_price')}</th><th>${t('th_actions')}</th>
      </tr></thead>`;
      const tb = document.createElement('tbody');
      products.forEach(p => {
        const tr = document.createElement('tr');
        tr.dataset.code = p.code;
        tr.innerHTML = `
          <td><span class="code-mono">${p.code}</span></td>
          <td>${p.description}</td>
          <td>${fmtPrice(p.price)}</td>
          <td>
            <div class="row-actions">
              <button class="btn btn-tonal btn-sm"
                      onclick="reactivate('${p.code}', this)">
                ${t('btn_reactivate')}
              </button>
              <button class="btn btn-danger-outline btn-sm"
                      onclick="deleteProduct('${p.code}', this)">
                ${t('btn_delete')}
              </button>
            </div>
          </td>
        `;
        tb.appendChild(tr);
      });
      tbl.appendChild(tb);
      wrap.appendChild(tbl);
      container.innerHTML = '';
      container.appendChild(wrap);
    } catch {
      container.innerHTML = `<p style="font-size:13px;color:var(--error);">${t('err_connection')}</p>`;
    }
  });
}

/* ── Reactivate / Delete (called from inline onclick) ───────────────────── */
async function reactivate(code, btn) {
  btn.disabled = true;
  try {
    const res = await api('/reactivar', 'PUT', { code });
    if (res.status === 'OK') {
      toast(t('toast_reactivated', { code }));
      btn.closest('tr').remove();
      cachedActive = [];
    } else {
      toast(res.error || t('err_connection'), 'error');
      btn.disabled = false;
    }
  } catch {
    toast(t('err_connection'), 'error');
    btn.disabled = false;
  }
}

async function deleteProduct(code, btn) {
  if (!confirm(t('confirm_delete', { code }))) return;
  btn.disabled = true;
  try {
    const res = await api('/eliminar', 'DELETE', { code });
    if (res.status === 'OK') {
      toast(t('toast_deleted', { code }));
      btn.closest('tr').remove();
      cachedActive = [];
    } else {
      toast(res.error || t('err_connection'), 'error');
      btn.disabled = false;
    }
  } catch {
    toast(t('err_connection'), 'error');
    btn.disabled = false;
  }
}

/* ── Close modals on backdrop click ─────────────────────────────────────── */
function initModalBackdrops() {
  document.querySelectorAll('.modal-backdrop').forEach(backdrop => {
    backdrop.addEventListener('click', e => {
      if (e.target === backdrop) backdrop.classList.remove('open');
    });
  });
}

/* ── Bootstrap ──────────────────────────────────────────────────────────── */
document.addEventListener('DOMContentLoaded', () => {
  applyI18n();
  initNav();
  initAddForm();
  initStockModal();
  initRetireModal();
  initFilters();
  initSettings();
  initModalBackdrops();
  loadProducts();
});
