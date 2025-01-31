from flask import Blueprint, request, render_template, redirect, flash, jsonify
import requests 
import json
import random

router = Blueprint('router', __name__)

@router.route('/')
def home():
    #r = requests.get("http://localhost:8080/api/censo/listCombustible")
    #data = r.json()
    #print(r.json())
    return render_template('base.html')



@router.route('/admin/vertice/register')
def register():
    return render_template('fragmento/Grafo/registro.html')




@router.route('/admin/graph/vertices')
def graph():
    r = requests.get("http://localhost:8088/myapp/city/list")
    ciudades = r.json().get('data', [])

    print("Ciudades enviadas a template:", ciudades)  # Verifica en la terminal

    return render_template('index.html', ciudades=ciudades) 




@router.route('/admin/graph/caminomascorto', methods=['GET', 'POST'])
def camino():
    r = requests.get("http://localhost:8088/myapp/city/list")
    ciudades = r.json().get('data', [])
    
    if request.method == 'POST':
        from_city = request.form.get('from_city')
        to_city = request.form.get('to_city')
        method = request.form.get('method')  # Obtener el método seleccionado
        
        print("Ciudad de Origen seleccionada:", from_city)  # Depuración
        print("Ciudad de Destino seleccionada:", to_city)   # Depuración
        print("Método seleccionado:", method)               # Depuración
        
        # Construir la URL del endpoint según el método seleccionado
        if method == "floyd":
            endpoint_url = f"http://localhost:8088/myapp/graph/floyd-warshall/{from_city}/{to_city}"
        elif method == "bellman":
            endpoint_url = f"http://localhost:8088/myapp/graph/bellman-ford/{from_city}/{to_city}"
        elif method == "bfs":
            endpoint_url = f"http://localhost:8088/myapp/graph/bfs/{from_city}/{to_city}"  # Nueva opción
        else:
            error_msg = "Método no válido."
            return render_template('fragmento/Grafo/lista.html', ciudades=ciudades, error_msg=error_msg)
        
        try:
            # Enviar la solicitud POST al backend
            response = requests.post(endpoint_url)
            print("Respuesta del backend:", response.json())  # Depuración
            
            # Verificar si la solicitud fue exitosa
            if response.status_code == 200:
                path_info = response.json().get('data', {})
                return render_template('fragmento/Grafo/lista.html', ciudades=ciudades, path_info=path_info)
            else:
                error_msg = f"Error al calcular el camino más corto. Código de estado: {response.status_code}"
                return render_template('fragmento/Grafo/lista.html', ciudades=ciudades, error_msg=error_msg)
        
        except requests.exceptions.RequestException as e:
            error_msg = f"Error de conexión con el backend: {str(e)}"
            return render_template('fragmento/Grafo/lista.html', ciudades=ciudades, error_msg=error_msg)
    
    return render_template('fragmento/Grafo/lista.html', ciudades=ciudades)





@router.route('/admin/graph/edges')
def generate_edges():
    # Obtener la lista de ciudades del backend
    r = requests.get("http://localhost:8088/myapp/graph/añadiradyacencias")
    ciudades = r.json().get('data', [])

    # Crear adyacencias aleatorias con pesos
    adyacencias = []
    num_ciudades = len(ciudades)

    if num_ciudades < 2:
        return jsonify({"error": "No hay suficientes ciudades para generar adyacencias"}), 400

    return jsonify({"adyacencias": adyacencias})




@router.route('/admin/vertice/save', methods=['POST'])
def save_graph():
    headers = {'Content-Type': 'application/json'}
    form = request.form

    dataF = {"nombre": form["nombre"], "lat": form["lat"], "lon": form["lon"], "habitantes": form["habitantes"]}
    try:
        r = requests.post('http://localhost:8088/myapp/city/save', data=json.dumps(dataF), headers=headers)

        if r.status_code == 200:
            flash("Vertice guardado correctamente", category="warning")
            return redirect('/admin/graph/vertices')
        else:
            dat = r.json()
            flash(str(dat.get("data", "Ocurrió un error")), category='error')
            return redirect('/admin/vertice/register')
    except requests.exceptions.RequestException as e:
        flash(f"Error al conectar con el servidor: {e}", category='error')
        return redirect('/admin/vertice/register')
    



