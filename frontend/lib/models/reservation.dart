import 'sector.dart';
class Reservation{
  final String id;
  final Sector sector;
  final DateTime startTime;
  final DateTime endTime;


  Reservation({
    required this.id,
    required this.sector,
    required this.startTime,
    required this.endTime,
  });

}