from flask import Blueprint, request, render_template, redirect, flash
import requests  # Asegúrate de que "requests" esté correctamente importado
import json
generatorroute = Blueprint('generatorroute', __name__)

@generatorroute.route('/')
def home():
    #r = requests.get("http://localhost:8080/api/censo/listCombustible")
    #data = r.json()
    #print(r.json())
    return render_template('base.html')



@generatorroute.route('/admin/generator/register')
def family_register():
    r = requests.get("http://localhost:8080/api/generator/listCombustible")
    data = r.json()
    print(r.json())
    return render_template('fragmento/generador/registro.html', lista = data["data"])



@generatorroute.route('/admin/generator/list')
def generator_list():
    r = requests.get("http://localhost:8080/api/generator/list")
    data = r.json()
    print(r.json())
    return render_template('fragmento/generador/lista.html', lista = data["data"])


@generatorroute.route('/admin/generator/search/<criterio>/<texto>')
def view_buscar_family(criterio, texto):
        url = "http://localhost:8080/api/censo/list/search/"
        if criterio  == "apellido":
            r = requests.get(url+texto)
            print("esta entrando aqui")
        elif criterio == "telefono":
            r = requests.get(url +"telefono/" + texto)
        data1 = r.json()
        
        if(r.status_code == 200):
            if type(data1["data"]) is dict:
                list=[]
                list.append(data1["data"])
                return render_template('fragmento/Familia/lista.html', lista = list)
            else:
                return render_template('fragmento/Familia/lista.html', lista = data1["data"])
        else:
                return render_template('fragmento/Familia/lista.html', lista = [], message="No se encontraron registros")
        
    


        



@generatorroute.route('/admin/generator/edit/<id>')
def view_edit_family(id):
    r = requests.get("http://localhost:8080/api/censo/listCombustible")
    data = r.json()
    r1 = requests.get("http://localhost:8080/api/censo/get/"+ id)
    data1 = r1.json()
    if(r1.status_code == 200):
        return render_template('fragmento/Familia/editar.html', lista = data["data"], person = data1["data"])
    else:
        flash(data=["data"], category='error')
        return redirect('/admin/family/list')





@generatorroute.route('/admin/generator/save', methods=['POST'])
def save_family():
    headers = {'Content-Type': 'application/json'}
    form = request.form

    dataF = {"family":form["family"],"uso":form["uso"],"combustible":form["combustible"],"consumo":form["consumo"], "costo":form["costo"],"energia":form["energia"]}
    r = requests.post('http://localhost:8080/api/generator/save', data=json.dumps(dataF), headers=headers)

    dat = r.json()
    if r.status_code == 200:
        flash("Registro guardado correctamente", category='success')
        return redirect('/admin/generator/list')
    else:
        flash(str(dat["data"], category='error')) 
        return redirect('/admin/family/register')
    


@generatorroute.route('/admin/generator/update', methods=['POST'])
def update_family():
    headers = {'Content-Type': 'application/json'}
    form = request.form

    dataF = {"id":form["id"],"apellido":form["apellido"],"direccion":form["direccion"],"telefono":form["telefono"],"uso":form["uso"],"combustible":form["combustible"],"consumo":form["consumo"],
             "costo":form["costo"],"energia_generada_KW":form["energia_generada_KW"]}
    r = requests.post('http://localhost:8080/api/censo/update', data=json.dumps(dataF), headers=headers)

    dat = r.json()
    if r.status_code == 200:
        flash("Registro guardado correctamente", category='success')
        return redirect('/admin/family/list')
    else:
        flash(str(dat["data"], category='error')) 
        return redirect('/admin/family/register')
    

