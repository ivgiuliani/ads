CC = gcc
CFLAGS = `pkg-config --cflags glib-2.0` -fPIC -O2 -funroll-loops -Wall -ggdb
LDFLAGS = `pkg-config --libs glib-2.0`
LIBFLAGS = -shared -Wl,-soname,libskip.so.1 -lc -DLIBSKIP

EXECUTABLE = skip
SOURCES = skip.c \
					main.c
OBJECTS = $(SOURCES:.c=.o)

all: $(SOURCES) $(EXECUTABLE)

$(EXECUTABLE): $(OBJECTS)
	$(CC) $(CFLAGS) $(LDFLAGS) $(OBJECTS) -o $@

.c.o:
	$(CC) -c $(CFLAGS) $< -o $@

clean:
	rm -f skip *.o *.so *.so.1

lib: $(SOURCES) libskip.so

libskip.so: $(OBJECTS)
	$(CC) $(LDFLAGS) $(OBJECTS) $(LIBFLAGS) -o $@
