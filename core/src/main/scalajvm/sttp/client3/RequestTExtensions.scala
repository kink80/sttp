package sttp.client3

import java.io.File
import java.nio.file.Path

import sttp.client3.internal.SttpFile

trait RequestTExtensions[U[_], T, -R] { self: RequestT[U, T, R] =>

  /** If content type is not yet specified, will be set to `application/octet-stream`.
    *
    * If content length is not yet specified, will be set to the length of the given file.
    */
  def body(file: File): RequestT[U, T, R] = body(SttpFile.fromFile(file))

  /** If content type is not yet specified, will be set to `application/octet-stream`.
    *
    * If content length is not yet specified, will be set to the length of the given file.
    */
  def body(path: Path): RequestT[U, T, R] = body(SttpFile.fromPath(path))

  // this method needs to be in the extensions, so that it has lowest priority when considering overloading options
  /** If content type is not yet specified, will be set to `application/octet-stream`.
    */
  def body[B: BodySerializer](b: B): RequestT[U, T, R] =
    withBody(implicitly[BodySerializer[B]].apply(b))
}
