import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgTemplateOutlet } from '@angular/common';
import { Usuario } from './usuario';
import { UsuariosService } from './usuarios.service';
import { ActivatedRoute } from '@angular/router';
import { Rol } from './rol';
import { RolesService } from './roles.service';
import swal from 'sweetalert2';

@Component({
  selector: 'app-lista-usuarios',
  templateUrl: './lista-usuarios.component.html',
  styleUrls: ['./lista-usuarios.component.css']
})
export class ListaUsuariosComponent implements OnInit {
  @ViewChild("myModalInfo", { static: false })
  myModalInfo!: TemplateRef<any>;
  content!: NgTemplateOutlet;
  usuarios: Usuario[] = [];
  roles: Rol[] = [];
  usuarioSeleccionado: Usuario = new Usuario();
  isNew: boolean = false;
  errores: string[] = [];
  nombreSearch: string = '';
  showedUsuarios: Usuario[] = [];


  constructor(private modalService: NgbModal,
    private usuariosService: UsuariosService,
    private rolesService: RolesService,
    private activatedRoute: ActivatedRoute,) {
  }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(() => {
      this.usuariosService.getUsuarios().subscribe(
        (response) => {
          this.usuarios = response as Usuario[];
          this.showedUsuarios = response as Usuario[];
          console.log(this.usuarios);
        }
      );
      this.rolesService.getRoles().subscribe(
        (response) => {
          this.roles = response as Rol[];
          console.log(this.roles);
        }
      );
    });
  }

  getInformation(usuario: Usuario) {
    this.isNew = false;
    this.usuarioSeleccionado = JSON.parse(JSON.stringify(usuario));
    this.modalService.open(this.myModalInfo);
  }

  openModalNewUsuario() {
    this.isNew = true;
    this.modalService.open(this.myModalInfo);
    this.usuarioSeleccionado = new Usuario();
  }

  search() {
    if (this.nombreSearch !== '') {
      this.showedUsuarios = this.usuarios.filter((usuario) => usuario.nombre.toUpperCase().includes(this.nombreSearch.toUpperCase()));
    } else {
      this.showedUsuarios = this.usuarios;
    }
  }
  cleanSearch() {
    this.nombreSearch = '';
    this.showedUsuarios = this.usuarios;
  }
  

  create() {
    this.usuariosService.create(this.usuarioSeleccionado).subscribe(
      () => {
        swal.fire('Exitoso!', 'Nuevo usuario creado con éxito');
        this.modalService.dismissAll(this.myModalInfo);
        this.ngOnInit();
      }, err => {
        this.errores = err.error.errors as string[];
        console.error('Código de error desde el backend:' + err.status);
        console.error(err.error.errors);
        this.modalService.dismissAll(this.myModalInfo);
      });
  }

  update() {
    console.log('this.usuarioSeleccionado', this.usuarioSeleccionado);
    this.usuariosService.update(this.usuarioSeleccionado).subscribe(
      () => {
        swal.fire('Exitoso!', 'Cliente actualizado con éxito');
        this.modalService.dismissAll(this.myModalInfo);
        this.ngOnInit();
      }, err => {
        this.errores = err.error.errors as string[];
        console.error('Código de error desde el backend:' + err.status);
        console.error(err.error.errors);
        this.modalService.dismissAll(this.myModalInfo);
      }
    )
  }
  delete() {
    swal.fire({
      title: 'Está seguro?',
      text: "Está seguro que desea eliminar al usuario?",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, eliminar!',
      cancelButtonText: 'No, cancelar!'
    }).then((result) => {
      if (result.isConfirmed) {
        this.usuariosService.delete(this.usuarioSeleccionado.idUsuario).subscribe(
          () => {
            swal.fire(
              'Eliminado!',
              `Usuario ${this.usuarioSeleccionado.nombre} eliminado con éxito.`,
              'success'
            );
            this.usuarioSeleccionado = new Usuario();
            this.ngOnInit();
            this.modalService.dismissAll(this.myModalInfo);
          }
        )
      }
    });

  }
}
